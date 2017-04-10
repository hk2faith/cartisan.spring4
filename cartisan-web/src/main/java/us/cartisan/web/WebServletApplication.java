package us.cartisan.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"us.cartisan"})
@MapperScan(basePackages = {"us.cartisan.dao"})
@EnableScheduling
public class WebServletApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(WebServletApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebServletApplication.class);
	}
}
