package com.larbi.hexa_order_b2b.infrastructure.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up OpenAPI documentation for the API.
 * This class configures the OpenAPI documentation using the Swagger library,
 * which generates a REST API documentation that can be easily accessed and viewed
 * in a web interface (e.g., Swagger UI).
 *
 * This configuration sets the title, description, and version of the API.
 */
@Configuration
public class OpenApiConfig {

        /**
         * Configures and provides the OpenAPI documentation for the API.
         *
         * @return a configured {@link OpenAPI} object that describes the API's information
         */
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new io.swagger.v3.oas.models.info.Info()
                                .title("Group hexa-order-b2b-api API")
                                .description("API for creating and joining group hexa-order-b2b-api.")
                                .version("v1"));
        }
}
