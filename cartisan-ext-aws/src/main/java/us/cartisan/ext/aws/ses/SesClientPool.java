/**
 * 
 */
package us.cartisan.ext.aws.ses;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
public class SesClientPool {
	private GenericObjectPool<SesClient> clientPool;
	
	public SesClientPool(AmazonSimpleEmailService service, int maxActive) {
		clientPool = new GenericObjectPool<SesClient>(new BasePoolableObjectFactory<SesClient>() {
			@Override
			public SesClient makeObject() throws Exception {
				log.debug("clientPool.makeObject() is called.");
				return new SesClient(service);
			}
		}, maxActive); //, 1000, GenericObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION, 1000);
		clientPool.setMaxIdle(maxActive);
		/*clientPool.setMaxActive(1000);
		clientPool.setMaxIdle(10);
		clientPool.setMaxWait(1000);
		clientPool.setTestOnBorrow(true);
		clientPool.setTestOnReturn(false);
		clientPool.setWhenExhaustedAction(GenericObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION);*/
		log.info("SesClientPool is created. maxActive={}", maxActive);
	}
	
	public SesClient getClient() throws Exception {
		log.debug("Current clientPool's status - [idle:{}, active:{}]and client is borrowed.", clientPool.getNumIdle(), clientPool.getNumActive());
		return clientPool.borrowObject();
	}
	
	public void returnClient(SesClient client) throws Exception {
		if (client != null) {
			clientPool.returnObject(client);
			log.debug("One client of clientPool is returned and clientPool.getNumIdle()={}", clientPool.getNumIdle());
		}
	}
	
	public void invalidateClient(SesClient client) throws Exception {
		clientPool.invalidateObject(client);
	}
	
	public void closeClients() throws Exception {
		clientPool.close();
	}
	
	public void print() {
		log.info("SesClientPool's max active : {} ",clientPool.getMaxActive());
		log.info("SesClientPool's active num : {} ",clientPool.getNumActive());
		log.info("SesClientPool's num idle : {} ",clientPool.getNumIdle());
	}
}
