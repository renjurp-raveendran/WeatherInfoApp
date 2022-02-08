package com.weatherinfoapp.WeatherInfoDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class WeatherInfoDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherInfoDemoApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/weatherinfo/*"))
				.apis(RequestHandlerSelectors.basePackage("com.weatherinfoapp.WeatherInfoDemo"))
				.build()
				.apiInfo(apiDetails());
	}
	private ApiInfo apiDetails() {
		return new ApiInfo(
			"Weather Info API",
			"API to get weather information based on city or coordinates ",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Renju","http://javacode.com","renjurp@gmail.com"),
				"API Licence",
				"http://localhost:9090/weatherinfo",
				Collections.emptyList()
		);
	}

}
