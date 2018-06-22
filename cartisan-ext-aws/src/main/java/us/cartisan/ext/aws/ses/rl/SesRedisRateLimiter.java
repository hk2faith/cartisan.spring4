package us.cartisan.ext.aws.ses.rl;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
@Data
public class SesRedisRateLimiter {
	private Object/*StringRedisClusterTemplate*/ stringRedisTemplate;
	private String key;
	private int permitsPerSec; // 아마존에서 제공하는 TPS 입력
	
	public SesRedisRateLimiter(Object/*StringRedisClusterTemplate*/ stringRedisTemplate, String key, int permitsPerSec) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.key = key;
		this.permitsPerSec = permitsPerSec;
	}

	public boolean isAvailable() {
		boolean available = false;
		Long currentValue = getValue();
		if (currentValue != null && currentValue > (long)permitsPerSec) {
			log.warn("In SesAccount[{}], too many requests(limit:{}, current:{}) per second!!", key, permitsPerSec,
				currentValue);
		} else {
			currentValue = incrementValue();
			if (currentValue == 1L) {
				setExpiration();
			}
			available = true;
		}
		return available;
	}
	
	private Long getValue() {
		String currentText = null; /*stringRedisTemplate.opsForValue().get(key);*/
		if (StringUtils.isEmpty(currentText)) {
			return null;
		} else {
			return Long.parseLong(currentText);
		}
	}
	
	private Long incrementValue() {
		Long currentValue = 0L; /*stringRedisTemplate.opsForValue().increment(key, 1L);*/
		return currentValue;
	}
	
	private void setExpiration() {
		/*stringRedisTemplate.expire(key, 1L, TimeUnit.SECONDS);*/
	}
}
