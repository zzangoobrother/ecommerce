package com.example.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterMemberRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotBlank(message = "이름을 입력해주세요.")
        String name
) {
}
