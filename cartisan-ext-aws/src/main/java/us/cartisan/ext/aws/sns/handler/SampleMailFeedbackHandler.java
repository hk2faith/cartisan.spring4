package us.cartisan.ext.aws.sns.handler;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import us.cartisan.ext.aws.ses.Ses;
import us.cartisan.ext.aws.sns.SnsMailFeedbackHandler;
import us.cartisan.ext.aws.sns.dto.SnsBounce;
import us.cartisan.ext.aws.sns.dto.SnsBouncedRecipient;
import us.cartisan.ext.aws.sns.dto.SnsComplainedRecipient;
import us.cartisan.ext.aws.sns.dto.SnsComplaint;
import us.cartisan.ext.aws.sns.dto.SnsMail;
import us.cartisan.ext.aws.sns.dto.SnsMessage;

/**
 * @author Hyungkook Kim
 */
@Slf4j
@Component
public class SampleMailFeedbackHandler extends SnsMailFeedbackHandler {
	@Override
	public Ses getSesAccount() {
		return Ses.authentication;
	}

	@Override
	public void onBounced(SnsMessage message) {
		try {
			SnsMail mail = message.getMail();
			SnsBounce bounce = message.getBounce();
			if (mail == null || bounce == null) {
				return;
			}

			final String messageId = mail.getMessageId();
			final List<SnsBouncedRecipient> bouncedRecipients = bounce.getBouncedRecipients();
			log.debug("\tstep 3-1. messageId : {}", messageId);

			if (!CollectionUtils.isEmpty(bouncedRecipients)) {
				for (SnsBouncedRecipient receipient : bouncedRecipients) {
					String email = receipient.getEmailAddress();
					log.warn(
						"bounced() is called and getSentEmailHashKey({}) is empty. bounceType:{}, subType:{}, email:{}",
						messageId, // 
						bounce.getBounceType(), bounce.getBounceSubType(), email);

					// TODO : 발송거부된(유효하지 않는) 이메일 처리
				}
			}
		} catch (Exception e) {
			log.error("Something WRONG?! bodyJson : {}", message, e);
		}
	}

	@Override
	public void onComplained(SnsMessage message) {
		try {
			SnsMail mail = message.getMail();
			SnsComplaint complaint = message.getComplaint();
			if (mail == null || complaint == null) {
				return;
			}

			final String messageId = mail.getMessageId();
			final List<SnsComplainedRecipient> complainedRecipients = complaint.getComplainedRecipients();
			log.debug("\tstep 3-1. messageId : {}", messageId);

			if (!CollectionUtils.isEmpty(complainedRecipients)) {
				for (SnsComplainedRecipient receipient : complainedRecipients) {
					String email = receipient.getEmailAddress();
					log.warn(
						"complained() is called and getSentEmailHashKey({}) is empty. complaintFeedbackType:{}, email:{}", // 
						messageId, complaint.getComplaintFeedbackType(), email);

					// TODO : 수신거부한 이메일 처리
				}
			}
		} catch (Exception e) {
			log.error("Something WRONG?! bodyJson : {}", message, e);
		}
	}
}
