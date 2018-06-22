package us.cartisan.ext.aws.sns.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SnsMessage {
	private String notificationType;
	private SnsMail mail;
	private SnsBounce bounce;
	private SnsComplaint complaint;
	private SnsDelivery delivery;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
