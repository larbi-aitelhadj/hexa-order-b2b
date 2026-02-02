package com.larbi.hexa_order_b2b.infrastructure.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to enable and configure Cross-Origin Resource Sharing (CORS) for the web application.
 * This class allows the application to define CORS settings, enabling or restricting resources to be shared
 * between different origins (domains).
 *
 * This configuration allows all origins, methods, headers, and credentials for cross-origin requests.
 * It is useful when you need to allow a front-end application hosted on a different domain or port
 * to access your backend APIs.
 */
@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    /**
     * Configures the CORS mappings for the application.
     *
     * This method allows cross-origin requests for all URLs and enables the following:
     * - All origins (`allowedOriginPatterns("*")`)
     * - All HTTP methods (`allowedMethods("*")`)
     * - All headers (`allowedHeaders("*")`)
     * - Allows credentials (cookies, authentication) to be sent in cross-origin requests (`allowCredentials(true)`)
     * - Sets the maximum time (in seconds) that the browser can cache pre-flight requests to 3600 seconds (1 hour).
     *
     * @param registry the {@link CorsRegistry} used to configure the CORS settings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS configuration to all endpoints
                .allowedOriginPatterns("*") // Allow requests from any origin
                .allowedMethods("*") // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true) // Allow cookies and authentication credentials in requests
                .maxAge(3600); // Set the max age for pre-flight requests to 1 hour (3600 seconds)
    }
}
