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
public class SnsBounce {
	
	/**
	 * bounceType : Amazon SES가 결정한 반송 메일의 유형
	 * bounceSubType : Amazon SES가 결정한 반송 메일의 하위 유형
	 * 
	 * bounceType		bounceSubType		설명
	 * ----------------------------------------------------------------------------------------
	 * Undetermined		Undetermined		Amazon SES가 특정 반송 사유를 결정하지 못했습니다.
	 * Permanent		General				Amazon SES가 일반 하드 바운스를 수신했으며, 이 수신자의 이메일 주소를 메일 발송 목록에서 제거할 것을 권장합니다.
	 * Permanent		NoEmail				대상 이메일 주소가 존재하지 않아 Amazon SES가 영구 하드 바운스를 수신했습니다. 이 수신자를 메일 발송 목록에서 제거할 것을 권장합니다.
	 * Permanent		Suppressed			최근의 잘못된 주소로 인한 반송 이력 때문에 Amazon SES가 이 주소로 메일 전송을 금지했습니다. 발송 금지 목록에서 주소를 제거하는 방법에 대한 자세한 내용은 Amazon SES 금지 목록에서 이메일 주소 제거 단원을 참조하십시오.
	 * Transient		General				Amazon SES가 일반 반송 메일을 수신했습니다. 향후 이 수신자에게 재전송이 성공할 수도 있습니다.
	 * Transient		MailboxFull			Amazon SES가 메일박스 가득 참 반송 메일을 수신했습니다. 향후 이 수신자에게 재전송이 성공할 수도 있습니다.
	 * Transient		MessageTooLarge		Amazon SES가 메시지 너무 큼 반송 메일을 수신했습니다. 메시지 크기를 줄일 경우 이 수신자에게 재전송이 성공할 수도 있습니다.
	 * Transient		ContentRejected		Amazon SES가 내용 거부 반송 메일을 수신했습니다. 메시지 내용을 변경할 경우 이 수신자에게 재전송이 성공할 수도 있습니다.
	 * Transient		AttachmentRejected	Amazon SES가 첨부 파일 거부 반송 메일을 수신했습니다. 첨부 파일을 제거 또는 변경할 경우 이 수신자에게 재전송이 성공할 수도 있습니다.
	 * 
	 */
	private String bounceType;
	private String bounceSubType;	// "General"
	private List<SnsBouncedRecipient> bouncedRecipients;
	private String reportingMTA;	// "example.com"
	private String timestamp;		// "2012-05-25T14:59:38.605Z"
	private String feedbackId;		// "000001378603176d-5a4b5ad9-6f30-4198-a8c3-b1eb0c270a1d-000000"
	private String remoteMtaIp;		// "127.0.2.0"
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
