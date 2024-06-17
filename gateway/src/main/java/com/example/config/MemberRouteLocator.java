package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class MemberRouteLocator {

    @Value("${route.member-api.v1.base-url}")
    private String baseUrl;

    private final String gatewayPath = "/api/v1/members/";

    @Order(0)
    @Bean
    public RouterFunction<ServerResponse> getMemberRoute() {
        return route()
                .route(path(gatewayPath + "**"), http(baseUrl))
                .before(rewritePath("/api/v1/(?<servicePath>.*)", "/${servicePath}"))
                .build();
    }
}
