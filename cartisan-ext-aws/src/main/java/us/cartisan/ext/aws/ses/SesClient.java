/**
 * 
 */
package us.cartisan.ext.aws.ses;

import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.GetSendQuotaResult;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendDataPoint;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
public class SesClient {
	protected AmazonSimpleEmailService service = null;

	public SesClient(AmazonSimpleEmailService service) {
		this.service = service;
	}
	
	public SendEmailResult send(String from, String[] to, String subjectText, String bodyHtml) throws AmazonServiceException, Exception {
		// Construct an object to contain the recipient address.
		final Destination destination = new Destination().withToAddresses(to);

		// Create the subject and body of the message.
		final Content subject = new Content().withData(subjectText);
		final Body body = new Body().withHtml(new Content().withData(bodyHtml));

		// Create a message with the specified subject and body.
		final Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(
			message);

		try {
			SendEmailResult result = service.sendEmail(request);
			log.debug("service.sendEmail({}) = {}", request, result);
			return result;
		} catch (AmazonServiceException ex) {
			log.error("Amazon SES Sending is failed. errCode : {}, errMsg : {}", ex.getErrorCode(),
				ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			log.error("Amazon SES Sending is failed. message : {}", ex.getMessage());
			throw ex;
		}
	}
	
	public void send(String from, String[] to, String subjectText, String bodyHtml, SesClientPostProcessor postProcessor) {
		if (postProcessor == null) {
			log.info("SesClient.send() is failed. postProcessor cannot be null.");
		}

		try {
			SendEmailResult result = send(from, to, subjectText, bodyHtml);
			postProcessor.onSuccess(result);
		} catch (AmazonServiceException ex) {
			if ("Throttling".equals(ex.getErrorCode())
				&& "Maximum sending rate exceeded.".equals(ex.getMessage())) {
				postProcessor.onExcessQuota();
			} else {
				postProcessor.onError(ex);
			}
		} catch (Exception ex) {
			postProcessor.onError(ex);
		}
	}
	
	public void send(SesMail mail, SesClientPostProcessor postProcessor) {
		send(mail.getFrom(), mail.getTo(), mail.getSubjectText(), mail.getBodyHtml(), postProcessor);
	}
	
	public SesQuota getQuotaInfo() {
		GetSendQuotaResult quotaResult = service.getSendQuota();
		log.debug("quota: {}", quotaResult);
		SesQuota result = new SesQuota();
		result.setMaxQuota(quotaResult.getMax24HourSend().longValue());
		result.setSentCount(quotaResult.getSentLast24Hours().longValue());
		
		return result;
	}
	
	public List<SendDataPoint> getStatistics() {
		return service.getSendStatistics().getSendDataPoints();
	}
}
