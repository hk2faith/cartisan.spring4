/**
 * 
 */
package us.cartisan.ext.aws.ses.rl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;
import us.cartisan.ext.aws.ses.Ses;
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
public class SesRedisRateLimitedExecuteManager {
	private final int WATING_MILLS = 10;
	
	private Ses ses;
	private SesRedisRateLimiter rateLimiter;
	private ExecutorService execPool;
	private SesClientPool clientPool;
	
	public SesRedisRateLimitedExecuteManager(Ses ses, SesRedisRateLimiter rateLimiter) {
		log.debug("Attempting to initialize Amazon SES for '{}' using the AWS SDK", ses.name());
		
		this.ses = ses;
		this.rateLimiter = rateLimiter;
		execPool = Executors.newFixedThreadPool(
			rateLimiter.getPermitsPerSec() * 2, //
			new ThreadFactoryBuilder() //
				.setNameFormat("SesRateLimitedExecutor-%d") //
				.setDaemon(true) //
				.build());
		
		AWSCredentialsProvider provider = new ClasspathPropertiesFileCredentialsProvider(
			ses.getCredentialFilePath());
		AmazonSimpleEmailService service = AmazonSimpleEmailServiceAsyncClientBuilder //
			.standard() //
			.withCredentials(provider) //
			.withRegion(ses.getRegions()) //
			.build(); //
		this.clientPool = new SesClientPool(service, rateLimiter.getPermitsPerSec() * 2);
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
			log.error("clientPool.returnClient({}) is failed. clientPool:{}", client, clientPool, ex);
		}
	}
	
	private void invalidateClient(SesClient client) {
		try {
			clientPool.invalidateClient(client);
		} catch (Exception ex) {
			log.error("clientPool.invalidate({}) is failed. clientPool:{}", client, clientPool, ex);
		}
	}
	
	public void exec(String from, String[] to, String subjectText, String bodyHtml, SesClientPostProcessor postProcessor) {
		execPool.execute(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if (rateLimiter.isAvailable()) {
						SesClient client = getClient();
						if (client != null) {
							client.send(from, to, subjectText, bodyHtml, postProcessor);
							returnClient(client);
							break;
						} else {
							log.warn("getClient() is null. SesClient must be non-nullable.");
							invalidateClient(client);
						}
					}
					
					log.debug("Waiting for sending during {}ms", WATING_MILLS);
					try { Thread.sleep(WATING_MILLS); } catch (InterruptedException e) { }
				}
			}
		});
	}
	
	public void exec(SesMail mail, SesClientPostProcessor postProcessor) {
		execPool.execute(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if (rateLimiter.isAvailable()) {
						SesClient client = getClient();
						if (client != null) {
							client.send(mail, postProcessor);
							returnClient(client);
							break;
						} else {
							log.warn("getClient() is null. SesClient must be non-nullable.");
							invalidateClient(client);
						}
					} else {
						log.debug("Waiting for sending during 200ms");
						try { Thread.sleep(WATING_MILLS); } catch (InterruptedException e) { }
					}
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
		} else {
			log.warn("getClient() is null. SesClient must be non-nullable.");
			invalidateClient(client);
		}
		return available;
	}
}
