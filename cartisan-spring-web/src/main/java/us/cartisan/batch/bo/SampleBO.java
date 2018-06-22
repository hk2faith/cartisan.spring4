/**
 * 
 */
package us.cartisan.batch.bo;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
@Service
public class SampleBO {
	public void calcDailyStats(String ymd) {
		log.info("calcHourlyStats({}) is called.", ymd);
		log.info("calcHourlyStats({}) ended successfully.", ymd);

	}

	public void calcHourlyStats(String ymdh) {
		log.info("calcHourlyStats({}) is called.", ymdh);
		log.info("calcHourlyStats({}) ended successfully.", ymdh);
	}

}
