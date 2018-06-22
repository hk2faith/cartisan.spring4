package us.cartisan.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hyungkook Kim
 */
@Controller
public class MonitorController {
	private boolean serverReady = true;

	@RequestMapping("/monitor/l7check")
	public ResponseEntity<String> l7Check(HttpServletResponse response) {
		if (serverReady) {
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
