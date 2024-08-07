package com.ecommerce.apigateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("PRODUCT-SERVICE", r -> r.path("/product/**")
                        .uri("lb://PRODUCT-SERVICE/**"))
                .route("AUTH-SERVICE", r -> r.path("/auth/**")
                        .uri("lb://AUTH-SERVICE/**"))
                .build();
    }

}
