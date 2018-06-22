/**
 * 
 */
package us.cartisan.ext.aws.ses.rl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;
import us.cartisan.ext.aws.ses.SesClient;
import us.cartisan.ext.aws.ses.SesClientPool;
import us.cartisan.ext.aws.ses.SesClientPostProcessor;
import us.cartisan.ext.aws.ses.SesMail;
import us.cartisan.ext.aws.ses.SesQuota;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
public class SesRateLimitedExecuteManager {
	private RateLimiter rateLimiter;
	private ExecutorService execPool;
	
	private SesClientPool clientPool;
	
	public SesRateLimitedExecuteManager(AmazonSimpleEmailService service, int permitsPerSec) {
		rateLimiter= RateLimiter.create(permitsPerSec);
		execPool = Executors.newFixedThreadPool(
			permitsPerSec  * 2, //
			new ThreadFactoryBuilder() //
				.setNameFormat("SesRateLimitedExecutor-%d") //
				.setDaemon(true) //
				.build());
		
		this.clientPool = new SesClientPool(service, permitsPerSec  * 2);
	}
	
	private SesClient getClient() {
		SesClient client = null;
		try {
			client = clientPool.getClient();
		} catch (Exception ex) {
			log.error("clientPool.getClient() is failed. clientPool:{}", clientPool, ex);
		}
		return client;
	}
	
	private void returnClient(SesClient client) {
		try {
			clientPool.returnClient(client);
		} catch (Exception ex) {
			log.error("clientPool.returnClient() is failed. clientPool:{}", clientPool, ex);
		}
	}
	
	public void exec(String from, String[] to, String subjectText, String bodyHtml, SesClientPostProcessor postProcessor) {
		execPool.execute(new Runnable() {
			@Override
			public void run() {
				rateLimiter.acquire();
				
				SesClient client = getClient();
				if (client != null) {
					client.send(from, to, subjectText, bodyHtml, postProcessor);
					returnClient(client);
				} else {
					log.warn("getClient() is null. SesClient must be non-nullable.");
				}
			}
		});
	}
	
	public void exec(SesMail mail, SesClientPostProcessor postProcessor) {
		execPool.execute(new Runnable() {
			@Override
			public void run() {
				rateLimiter.acquire();
				
				SesClient client = getClient();
				if (client != null) {
					client.send(mail, postProcessor);
					returnClient(client);
				}
			}
		});
	}
	
	public void stop() {
		final List<Runnable> rejected = execPool.shutdownNow();
		log.debug("Rejected tasks : {}", rejected.size());
	}
	
	public void close() {
		try {
			clientPool.closeClients();
			execPool.shutdown();
			
			while(!execPool.isShutdown()) {
				boolean done = execPool.awaitTermination(3, TimeUnit.SECONDS);
				
				log.debug("All SesRateLimitedExecutors were completed so far? {}", done);
				if (done) {
					break;
				}
			} 
		} catch (InterruptedException e) {
			log.error("Something WRONG?!", e);
		} catch (Exception e) {
			log.error("Something WRONG?!", e);	
		}
	}
	
	public boolean isAvailable() {
		return isAvailable(1);
	}

	public boolean isAvailable(int numberOfIncomingMail) {
		boolean available = false;
		SesClient client = getClient();
		if (client != null) {
			SesQuota quata = client.getQuotaInfo();
			if (quata != null) {
				log.debug("current QuotaInfo : {}", quata);
				available = (quata.getRemainQuota() >= numberOfIncomingMail);
			}
			returnClient(client);
		}
		return available;
	}
}
