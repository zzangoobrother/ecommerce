package com.example.controller.dto.request;

public record LoginMemberRequest(
        String loginId,
        String password
) {
}
