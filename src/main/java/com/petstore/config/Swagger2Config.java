package com.petstore.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String GROUP_NAME = "PETSTOREAPI-v%s";

    /**
     * @return Docket
     */
    @Bean
    public Docket swaggerApi1() {
        final String version = "1";
        return buildDocket(version);
    }

    private Docket buildDocket(String version) {
        return new Docket(DocumentationType.SWAGGER_2).groupName(String.format(GROUP_NAME, version))
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiEndPointsInfo(version));
    }

    private ApiInfo apiEndPointsInfo(String version) {
        return new ApiInfoBuilder().title("PETSTOREAPI API")
            .description("Documentation PETSTOREAPI API v" + version)
            .contact(new Contact("Sarvesh Padwal", "https://www.google.com", "sarvyaf521@gmail.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version(version)
            .build();
    }
}
