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
public class BadRequestException extends BaseException {
	public BadRequestException(int code, String message) {
		super(code, message);
	}
}
