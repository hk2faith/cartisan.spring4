/**
 * 
 */
package us.cartisan.ext.aws.ses;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SesMail {
	private String from;
	private String[] to;
	private String subjectText;
	private String bodyHtml;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
