package us.cartisan.ext.aws.ses;

import com.amazonaws.services.simpleemail.model.SendEmailResult;

/**
 * @author Hyungkook Kim
 */
public interface SesClientPostProcessor {
	public void onSuccess(SendEmailResult result);

	public void onError(Throwable error);

	public void onExcessQuota();
}
