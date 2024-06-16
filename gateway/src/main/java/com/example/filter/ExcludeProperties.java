package com.example.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "exclude")
public record ExcludeProperties(
        List<String> uri
) {
}
