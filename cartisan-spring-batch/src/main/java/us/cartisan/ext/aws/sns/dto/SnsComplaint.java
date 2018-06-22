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
public class SnsComplaint {
	
	/**
	 * complaintFeedbackType : ISP로부터 수신된 피드백 보고서의 Feedback-Type 필드의 값. 이 값은 피드백의 유형을 포함합니다.
	 * 
	 * abuse–원치 않는 이메일 또는 기타 유형의 이메일 침해를 나타냅니다.
	 * auth-failure–이메일 인증 실패 보고서.
	 * fraud–일종의 사기 또는 피싱 활동을 나타냅니다.
	 * not-spam–보고서를 제공하는 엔터티가 메시지를 스팸으로 간주하지 않음을 나타냅니다. 이는 스팸으로 잘못 태그 지정 또는 분류된 메시지를 교정하기 위해 사용될 수 있습니다.
	 * other–다른 등록된 유형에 들어맞지 않는 기타 피드백을 나타냅니다.
	 * virus–발원 메시지에서 바이러스가 발견되었다는 보고서.
	 * 
	 */
	private String complaintFeedbackType;	// "abuse"
	private String userAgent;				// "AnyCompany Feedback Loop (V0.01)"
	private List<SnsComplainedRecipient> complainedRecipients;
	private String arrivalDate; 			// "2009-12-03T04:24:21.000-05:00"
	private String timestamp;				// "2012-05-25T14:59:38.623Z"
	private String feedbackId;				// "000001378603177f-18c07c78-fa81-4a58-9dd1-fedc3cb8f49a-000000"
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
