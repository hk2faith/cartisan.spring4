package us.cartisan.web.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author  Hyunju Shin
 */
@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handlerMethod)
		throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("request: {}", request.getRequestURL());
			for (String value : request.getParameterMap().keySet()) {
				log.debug("{}: {}", value, request.getParameter(value));
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	}
}
