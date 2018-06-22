package us.cartisan.batch.task.model;

import java.text.SimpleDateFormat;

/**
 * @author HyungKook Kim
 *
 */
public enum JobScheduleType {
	HOURLY("yyyyMMddHH")
	, DAILY("yyyyMMdd")
	, WEEKLY("yyyyMMdd")
	, MONTHLY("yyyyMMdd")
	, PER5MINUTE("yyyyMMddHHmmss")
	, PER10MINUTE("yyyyMMddHHmmss") 
	;
	
	private String format;
	private SimpleDateFormat formatter;

	private JobScheduleType(String format) {
		this.format = format;
		this.formatter = new SimpleDateFormat(format);
	}

	public String getFormat() {
		return format;
	}

	public SimpleDateFormat getFormatter() {
		return formatter;
	}
	
	public static JobScheduleType findByName(String name) {
		if (name != null) {
			for (JobScheduleType each : JobScheduleType.values()) {
				if (each.name().equals(name)) {
					return each;
				}
			}
		}
		return null;
	}
}
