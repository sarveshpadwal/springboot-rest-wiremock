package com.petstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
