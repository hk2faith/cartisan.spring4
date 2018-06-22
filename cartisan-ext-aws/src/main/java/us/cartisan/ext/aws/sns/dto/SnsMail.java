/**
 * 
 */
package us.cartisan.ext.aws.sns.dto;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SnsMail {
	private String timestamp;					// "2016-01-27T14:05:45 +0000"
	private String messageId;					// "000001378603177f-7a5433e7-8edb-42ae-af10-f0181f34d6ee-000000"
	private String source;						// "john@example.com"
	private String sourceArn;					// "arn:aws:ses:us-west-2:888888888888:identity/example.com"
	private String sourceIp;					// "127.0.3.0"
	private String sendingAccountId;			// "123456789012"
	private List<String> destination;			// ["jane@example.com"]
	private boolean headersTruncated;			// false
	private List<Object> headers;
	private Map<String, Object> commonHeaders;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
