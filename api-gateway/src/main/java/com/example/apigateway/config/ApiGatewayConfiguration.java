package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator getRoutLocator(RouteLocatorBuilder builder){
        return builder
                .routes()
                    .route(predicateSpec -> predicateSpec.path("/cambio-service/**").uri("lb://cambio-service"))
                    .route(predicateSpec -> predicateSpec.path("/book-service/**").uri("lb://book-service"))
                .build();
    }

}
