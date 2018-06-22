/**
 * 
 */
package us.cartisan.core.exception.web;

/**
 * @author Hyungkook Kim
 *
 */
@SuppressWarnings("serial")
public class InvalidParameterApiException extends InvalidParameterException {
	public InvalidParameterApiException(int code, String message) {
		super(code, message);
	}
}
