/**
 * 
 */
package us.cartisan.ext.aws.sns.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SnsBouncedRecipient {
	private String emailAddress;	// "recipient@example.com"
	private String action;			// "failed"
	private String status;			// "5.0.0"
	private String diagnosticCode;	// "X-Postfix; unknown user"
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
