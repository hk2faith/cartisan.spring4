/**
 * 
 */
package us.cartisan.core.exception.web;

/**
 * @author Hyungkook Kim
 *
 */
@SuppressWarnings("serial")
public class BadRequestApiException extends BadRequestException {
	public BadRequestApiException(int code, String message) {
		super(code, message);
	}
}
