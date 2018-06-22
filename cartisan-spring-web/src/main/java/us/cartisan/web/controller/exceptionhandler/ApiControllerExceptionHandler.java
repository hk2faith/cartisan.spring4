package us.cartisan.web.controller.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import us.band.email.core.exception.BaseEmailException;
import us.band.email.core.model.BaseApiResult;
import us.band.email.core.model.BaseApiResult.ReturnCode;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiControllerExceptionHandler {

	@ResponseBody
	@ExceptionHandler(BaseEmailException.class)
	public BaseApiResult adApiErrorHandler(HttpServletRequest request, BaseEmailException e) {
		StringBuilder sb = new StringBuilder();
		sb.append("AdApiException : " + e.getResultMessage());
		log.error(sb.toString(), e);
		return new BaseApiResult(e.getResultCode(), e.getResultMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public BaseApiResult missingRequestParameterErrorHandler(HttpServletRequest request,
		MissingServletRequestParameterException e) {
		StringBuilder sb = new StringBuilder();
		sb.append("Parameter Exception : " + e.getParameterName() + " is missing.\n");
		log.error(sb.toString(), e);
		return new BaseApiResult(ReturnCode.ERROR_MISSING_PARAMETER.getCode(), e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public BaseApiResult defaultErrorHandler(HttpServletRequest request, Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append("Unknown Exception : " + e.getLocalizedMessage());
		log.error(sb.toString(), e);
		/*if(e instanceof NullPointerException) {
			log.error("stacktrace : {}", Lists.newArrayList(Thread.currentThread().getStackTrace()).stream().map(i -> i.toString()).collect(Collectors.joining("\n\t\t"))); 
		}*/
		return new BaseApiResult(ReturnCode.ERROR_UNKNOWN.getCode(), e.getMessage());

	}
}
