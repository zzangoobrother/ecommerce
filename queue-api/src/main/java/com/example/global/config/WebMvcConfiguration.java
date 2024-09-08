package com.example.global.config;

import com.example.global.config.auth.AuthMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthMemberArgumentResolver authMemberArgumentResolver;

    public WebMvcConfiguration(AuthMemberArgumentResolver authMemberArgumentResolver) {
        this.authMemberArgumentResolver = authMemberArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authMemberArgumentResolver);
    }
}
