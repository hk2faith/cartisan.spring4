package us.cartisan.ext.aws.sns.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SnsNotification {
	@JsonProperty("Type") 
	private String type; // "Notification"
	@JsonProperty("MessageId") 
	private String messageId; // "22b80b92-fdea-4c2c-8f9d-bdfb0c7bf324"
	@JsonProperty("TopicArn")
	private String topicArn; // "arn:aws:sns:us-west-2:123456789012:MyTopic"
	@JsonProperty("Subject")
	private String subject; // "My First Message"
	@JsonProperty("Message")
	private String message; // "Hello world!"
	@JsonProperty("Timestamp")
	private String timestamp; //"2012-05-02T00:54:06.655Z"
	@JsonProperty("SignatureVersion")
	private String signatureVersion; // "1"
	@JsonProperty("Signature")
	private String signature; // "EXAMPLEw6JRNwm1LFQL4ICB0bnXrdB8ClRMTQFGBqwLpGbM78tJ4etTwC5zU7O3tS6tGpey3ejedNdOJ+1fkIp9F2/LmNVKb5aFlYq+9rk9ZiPph5YlLmWsDcyC5T+Sy9/umic5S0UQc2PEtgdpVBahwNOdMW4JPwk0kAJJztnc="
	@JsonProperty("UnsubscribeURL")
	private String unsubscribeURL; // "https://sns.us-west-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:us-west-2:123456789012:MyTopic:c9135db0-26c4-47ec-8998-413945fb5a96"

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
