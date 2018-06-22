/**
 * 
 */
package us.cartisan.core.exception.web;

import us.cartisan.core.exception.BaseException;

/**
 * @author Hyungkook Kim
 *
 */
@SuppressWarnings("serial")
public class AuthException extends BaseException {
	public AuthException(int code, String message) {
		super(code, message);
	}
}
