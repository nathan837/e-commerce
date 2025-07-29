package com.nathdev.e_commerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/coldMarket/**")  // More specific than /** for security
            .allowedOrigins(
                "http://127.0.0.1:5500",
                "http://localhost:5500",  // Add both common localhost variants
                "http://localhost:9090"   // Include your backend origin if needed
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowedHeaders("*")
            .exposedHeaders("Authorization")  // Explicitly expose Authorization header
            .allowCredentials(true)
            .maxAge(3600);  // Cache preflight response for 1 hour
    }
}