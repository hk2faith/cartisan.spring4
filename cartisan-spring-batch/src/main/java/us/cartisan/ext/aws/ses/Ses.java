/**
 * 
 */
package us.cartisan.ext.aws.ses;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
public enum Ses {
	marketing("/aws/marketing/credential.properties", Regions.US_WEST_2, "BAND", "band-noreply@campmobile.com"), // 마케팅용 noreply@band.com
	notification("/aws/notification/credential.properties", Regions.US_WEST_2, "BAND", "band-noreply@campmobile.com"), // 서비스 알림용
	authentication("/aws/authentication/credential.properties", Regions.US_WEST_2, "BAND", "band-noreply@campmobile.com"); // 인증용
	
	private String credentialFilePath;
	private Regions regions;
	private String senderName;
	private String senderEmail;
	
	private Ses(String credentialFilePath, Regions regions, String senderName, String senderEmail) {
		this.credentialFilePath = credentialFilePath;
		this.regions = regions;
		this.senderName = senderName;
		this.senderEmail = senderEmail;
	}

	public String getCredentialFilePath() {
		return credentialFilePath;
	}

	public Regions getRegions() {
		return regions;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public static AmazonSimpleEmailService getService(Ses ses) {
		log.debug("Attempting to initialize Amazon SES for '{}' using the AWS SDK", ses.name());
		
		AWSCredentialsProvider provider = new ClasspathPropertiesFileCredentialsProvider(
			ses.credentialFilePath);
		AmazonSimpleEmailService service = AmazonSimpleEmailServiceAsyncClientBuilder //
			.standard() //
			.withCredentials(provider) //
			.withRegion(ses.regions) //
			.build(); //
		return service;
	}
	
	public static Ses getSes(String sesName) {
		for (Ses ses : Ses.values()) {
			if (ses.name().equals(sesName)) {
				return ses;
			}
		}
		return null;
	}
}
