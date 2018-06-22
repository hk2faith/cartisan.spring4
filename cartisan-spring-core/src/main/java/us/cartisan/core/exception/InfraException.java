/**
 * 
 */
package us.cartisan.core.exception;

/**
 * @author Hyungkook Kim
 *
 */
@SuppressWarnings("serial")
public class InfraException extends BaseException {
	public InfraException(int code, String message) {
		super(code, message);
	}
}
