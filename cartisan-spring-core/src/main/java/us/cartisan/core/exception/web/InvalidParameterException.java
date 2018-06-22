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
public class InvalidParameterException extends BaseException {
	public InvalidParameterException(int code, String message) {
		super(code, message);
	}
}
