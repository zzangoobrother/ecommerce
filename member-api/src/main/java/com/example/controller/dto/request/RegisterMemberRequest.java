package com.example.controller.dto.request;

public record RegisterMemberRequest(
        String loginId,
        String password,
        String name
) {
}
