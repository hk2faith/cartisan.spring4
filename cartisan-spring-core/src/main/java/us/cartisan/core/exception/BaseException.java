package us.cartisan.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hyungkook Kim
 *
 */
@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper=false)
public abstract class BaseException extends RuntimeException {
	private int code;
	private String message;

	public BaseException(int code, String message) {
		super(String.format("[%d]%s", code, message));
		
		this.code = code;
		this.message = message;
	}
}
