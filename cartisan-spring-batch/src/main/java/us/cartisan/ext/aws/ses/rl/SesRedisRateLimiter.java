package us.cartisan.ext.aws.ses.rl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
@Data
public class SesRedisRateLimiter {
	private static final long EXPIRE_SECONDS = 1L;
	
	private Object/*StringRedisClusterTemplate*/ stringRedisTemplate;
	private String key = "email.sesRateLimit_";
	private int permitsPerSec;
	
	public SesRedisRateLimiter(Object/*StringRedisClusterTemplate*/ stringRedisTemplate, String keyName, int permitsPerSec) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.key = keyName;
		this.permitsPerSec = permitsPerSec;
	}

	public boolean isAvailable() {
		boolean available = false;
		String currentText = null; /*stringRedisTemplate.opsForValue().get(key);*/
		long currentValue = 0L;
		if (currentText != null) {
			currentValue = Long.parseLong(currentText);
			if (currentValue > new Long(permitsPerSec)) {
				log.warn("In SesAccount[{}], too many requests(limit:{}, current:{}) per second!!", key, permitsPerSec, currentValue);
			}
		} else {
			currentValue = 0L; /*stringRedisTemplate.opsForValue().increment(key, 1L);*/
			if (currentValue == 1L) {
				/*stringRedisTemplate.expire(key, EXPIRE_SECONDS, TimeUnit.SECONDS);*/
			}
			available = true;
		}
		return available;
	}
}
