package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EcommerceRouteLocator {

    @Value("${route.ecommerce-api.v1.base-url}")
    private String baseUrl;

    private final String gatewayPath = "/api/v1/";

    @Bean
    public RouteLocator customEcommerceRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("ecommerce-api",
                        r -> r.path(gatewayPath + "**")
                                .filters(f -> f.rewritePath(gatewayPath + "(?<servicePath>.*)", "/${servicePath}"))
                                .uri(baseUrl)
                ).build();
    }
}
