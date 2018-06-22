package us.cartisan.ext.aws.sns;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.cartisan.ext.aws.ses.Ses;

/**
 * @author Hyungkook Kim
 */
@Component
public class SnsMailFeedbackHandlerDispatcher {
	@Autowired
	private List<SnsMailFeedbackHandler> handlers;

	private Map<Ses, SnsMailFeedbackHandler> handlerMap;

	@PostConstruct
	void init() {
		handlerMap = handlers.stream().collect(
			Collectors.toMap(SnsMailFeedbackHandler::getSesAccount, Function.identity()));
	}

	public SnsMailFeedbackHandler dispatch(String sesName) {
		return handlerMap.get(Ses.getSes(sesName));
	}
}
