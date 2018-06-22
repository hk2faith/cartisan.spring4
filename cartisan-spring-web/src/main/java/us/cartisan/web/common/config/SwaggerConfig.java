package us.cartisan.web.common.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hyungkook Kim
 */
@Configuration
@EnableSwagger2
@Profile(value = {"dev", "stage"})
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2) //
			.select() //
			.paths(paths()) //
			.build() //
			.apiInfo(apiInfo()) //
			.useDefaultResponseMessages(false) //
		;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder() //
			.title("BAND::MAIL REST API") //
			.description("BAND::MAIL REST API.") //
			// .termsOfServiceUrl("http://band.us") //
			//.contact("springfox")
			//.license("Apache License Version 2.0")
			// .licenseUrl("https://adcenter.band.us/public-common/personal") //
			// .version("1.0") //
			.build();
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return Predicates.<String>or( //
			regex("/v.*/mail/.*") //
			, regex("/v.*/awsSns/.*") //
			, regex("/v.*/extraction/.*") //
		);
	}

	/*@Bean
	public ApplicationListener<ObjectMapperConfigured> objectMapperConfiguredListener() {
	
		return new ApplicationListener<ObjectMapperConfigured>() {
	
			@Override
			public void onApplicationEvent(ObjectMapperConfigured event) {
	
				//logger.info("listening onApplicationEvent");
	
				// joda time 처리를 위한 모듈 추가 
				//event.getObjectMapper().registerModule(new JodaModule());
	
			}
	
		};
	
	}*/
}
