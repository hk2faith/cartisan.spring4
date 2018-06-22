/**
 * 
 */
package us.cartisan.ext.aws.sns.dto;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SnsDelivery {
	private String timestamp;				// "2014-05-28T22:41:01.184Z"
	private String processingTimeMillis;	// 546
	private List<String> recipients;		// ["success@simulator.amazonses.com"]
	private String smtpResponse;			// "250 ok:  Message 64111812 accepted"
	private String reportingMTA;			// "a8-70.smtp-out.amazonses.com"
	private String remoteMtaIp;				// "127.0.2.0"

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
