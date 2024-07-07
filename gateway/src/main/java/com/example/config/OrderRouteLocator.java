package com.example.config;

import com.example.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class OrderRouteLocator {

    @Value("${route.order-api.v1.base-url}")
    private String baseUrl;

    private final String gatewayPath = "/api/v1/";

    @Order(1)
    @Bean
    public RouteLocator getOrderRoute(RouteLocatorBuilder routeLocatorBuilder, AuthFilter authFilter) {
        return routeLocatorBuilder.routes()
                .route("order-api",
                        r -> r.path(gatewayPath + "orders/**")
                                .filters(f -> f.circuitBreaker(c -> c.setName("ecommerce-circuit-breaker").setFallbackUri("forward:/fallback-circuit"))
                                        .filter(authFilter.apply(new AuthFilter.Config()))
                                        .rewritePath(gatewayPath + "(?<servicePath>.*)", "/${servicePath}"))
                                .uri(baseUrl)
                )
                .build();
    }
}
