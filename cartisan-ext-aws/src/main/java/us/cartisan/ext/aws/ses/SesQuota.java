package us.cartisan.ext.aws.ses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Hyungkook Kim
 *
 */
@Data
public class SesQuota {
	private long sentCount;
	private long maxQuota;

	@JsonIgnore
	public long getRemainQuota() {
		return maxQuota - sentCount;
	}
}
