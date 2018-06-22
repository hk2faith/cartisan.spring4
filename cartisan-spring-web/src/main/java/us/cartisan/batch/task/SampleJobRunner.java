package us.cartisan.batch.task;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import us.cartisan.batch.bo.SampleBO;
import us.cartisan.batch.task.model.JobScheduleType;

/**
 * @author Hyungkook Kim
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"us.cartisan.batch", "us.cartisan.common"})
public class SampleJobRunner {
	private static final String NAMESPACE = SampleJobRunner.class.getName();
	
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(SampleJobRunner.class);
		app.setWebEnvironment(false);
		//app.setShowBanner(false);
		
		ConfigurableApplicationContext ctx = app.run(args);
		SampleBO sampleBO = ctx.getBean(SampleBO.class);
		try {
			JobScheduleType jobRunType = JobScheduleType.findByName(args[0]);
			if (JobScheduleType.DAILY == jobRunType) {
				String ymd = JobScheduleType.DAILY.getFormatter().format(DateUtils.addDays(new Date(), -1));
				if (args.length == 2) {
					ymd = args[1];
				}
				
				sampleBO.calcDailyStats(ymd);
			} else if (JobScheduleType.HOURLY  == jobRunType) {
				String ymdh = JobScheduleType.HOURLY.getFormatter().format(DateUtils.addHours(new Date(), -1));
				if (args.length == 2) {
					ymdh = args[1];
				}
				
				sampleBO.calcHourlyStats(ymdh);
			} else {
				System.out.println(NAMESPACE + " can not execute(args=" + Arrays.asList(args) + ")");
			}
		} finally {
			ctx.close();
		}
	}
}
