package com.ysavitski.example.springmvcandangularjs.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String SECURITY_REFERENCE_TEST_NAME = "test";

	enum AuthorizationScopes {
		READ("read", "read access");

		AuthorizationScopes(final String type, final String description) {
			this.type = type;
			this.description = description;
		}

		private String type;

		private String description;

		public String getType() {
			return type;
		}

		public String getDescription() {
			return description;
		}
	}

	@Bean
	public Docket userApi() {
		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = new AuthorizationScopeBuilder()
				.scope(AuthorizationScopes.READ.getType())
				.description(AuthorizationScopes.READ.getDescription())
				.build();
		final SecurityReference securityReference = SecurityReference.builder()
				.reference(SECURITY_REFERENCE_TEST_NAME)
				.scopes(authorizationScopes)
				.build();
		final ArrayList<SecurityContext> securityContexts = Lists.newArrayList(
				SecurityContext.builder().securityReferences(Lists.newArrayList(securityReference)).build()
		);

		return new Docket(DocumentationType.SWAGGER_2)
				.directModelSubstitute(LocalDateTime.class, String.class)
				/*.ignoredParameterTypes(User.class)*/
				.securitySchemes(Lists.newArrayList(new BasicAuth(SECURITY_REFERENCE_TEST_NAME)))
				.securityContexts(securityContexts)
				.apiInfo(apiInfo())
				.select()
				.paths(apiPaths())
				.build();
	}

	private Predicate<String> apiPaths() {
		return Predicates.or(PathSelectors.regex("/api/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AngularJS Spring MVC Example API")
				.description("The online reference documentation for developers")
				.contact(new Contact("Yauheni Savitski", null, "07ersh@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("2.0")
				.build();
	}
}
