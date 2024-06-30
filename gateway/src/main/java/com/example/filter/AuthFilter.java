package com.example.filter;

import com.example.repository.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private static final String SESSION_KEY = "JSESSIONID";
    private static final String REDIS_SESSION_KEY = ":sessions:";
    private String namespace = "spring:session";
    
    private final ExcludeProperties excludeProperties;
    private final RedisRepository redisRepository;
    
    public AuthFilter(ExcludeProperties excludeProperties, RedisRepository redisRepository) {
        super(Config.class);
        this.excludeProperties = excludeProperties;
        this.redisRepository = redisRepository;
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            for (String excludeUri : excludeProperties.uri()) {
                if (path.equalsIgnoreCase(excludeUri)) {
                    return chain.filter(exchange);
                }
            }

            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            if (cookies == null || cookies.isEmpty() || !cookies.containsKey(SESSION_KEY)) {
                log.warn("Session key not exists");
                return failAuthResponse(exchange);
            }

            HttpCookie httpCookie = cookies.get(SESSION_KEY).get(0);
            String sessionID  = httpCookie.getValue();
            String decodedSessionId = new String(Base64.getDecoder().decode(sessionID.getBytes()));
            if (!redisRepository.hasKey(namespace + REDIS_SESSION_KEY + decodedSessionId)) {
                return failAuthResponse(exchange);
            }

            return chain.filter(exchange);
        };
    }
    
    private Mono<Void> failAuthResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {}
}
