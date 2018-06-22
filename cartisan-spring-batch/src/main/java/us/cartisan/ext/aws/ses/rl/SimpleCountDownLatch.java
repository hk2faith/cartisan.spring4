package us.cartisan.ext.aws.ses.rl;

import java.util.concurrent.CountDownLatch;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hyungkook Kim
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleCountDownLatch extends CountDownLatch {
	volatile int successCount = 0;
	volatile int failCount = 0;

	public SimpleCountDownLatch(int count) {
		super(count);
	}

	public void countDownOnSuccess() {
		successCount++;
		countDown();
	}

	public void countDownOnFail() {
		failCount++;
		countDown();
	}

	public void countDownAll() {
		while (getCount() > 0) {
			countDown();
		}
	}
}
