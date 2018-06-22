package us.cartisan.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author  Hyunju Shin
 */
public class DateUtil {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private DateUtil() {}

	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMAT.format(date);
	}

	public static DateTime parse(String dateString, String zoneId) {
		return new DateTime(dateString, DateTimeZone.forID(zoneId));
	}
}
