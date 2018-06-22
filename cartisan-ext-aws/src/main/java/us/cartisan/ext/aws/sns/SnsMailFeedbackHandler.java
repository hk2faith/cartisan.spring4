package us.cartisan.ext.aws.sns;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import us.cartisan.core.util.json.jackson.JacksonObjectMapper;
import us.cartisan.ext.aws.ses.Ses;
import us.cartisan.ext.aws.sns.dto.SnsMessage;
import us.cartisan.ext.aws.sns.dto.SnsNotification;

/**
 * @author Hyungkook Kim
 */
@Slf4j
public abstract class SnsMailFeedbackHandler {
	protected ObjectMapper objectMapper = JacksonObjectMapper.getCamelMapper();

	public abstract Ses getSesAccount();

	public abstract void onBounced(SnsMessage message);

	public abstract void onComplained(SnsMessage message);

	private SnsMessage convert(final String bodyJson) {
		SnsMessage message = null;
		try {
			log.debug("step 1. body : {}", bodyJson);
			SnsNotification notification = objectMapper.readValue(bodyJson, SnsNotification.class);
			log.debug("step 2. notification : {}", notification);

			message = objectMapper.readValue(notification.getMessage(), SnsMessage.class);
			log.debug("step 3. message : {}", message);

		} catch (Exception e) {
			log.error("Something WRONG?! bodyJson : {}", bodyJson, e);
		}
		return message;
	}

	public void bounced(final String bodyJson) {
		if (StringUtils.isEmpty(bodyJson)) {
			log.warn("bounced('') is called.");
			return;
		}

		SnsMessage message = convert(bodyJson);
		if (message != null) {
			onBounced(message);
		}
	}

	public void complained(final String bodyJson) {
		if (StringUtils.isEmpty(bodyJson)) {
			log.warn("complained('') is called.");
			return;
		}

		SnsMessage message = convert(bodyJson);
		if (message != null) {
			onComplained(message);
		}
	}
}
