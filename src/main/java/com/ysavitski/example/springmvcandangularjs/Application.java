package com.ysavitski.example.springmvcandangularjs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder(final JsonComponentModule jsonComponentModule) {
		return new Jackson2ObjectMapperBuilder()
				.serializationInclusion(JsonInclude.Include.NON_EMPTY)
				.featuresToDisable(
						SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
						DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
				)
				.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
				.indentOutput(true)
				.modulesToInstall(jsonComponentModule);
	}

}
