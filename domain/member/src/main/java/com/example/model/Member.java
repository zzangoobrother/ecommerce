package com.example.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String loginId;
    private String password;
    private String name;

    @Builder
    public Member(Long id, String loginId, String password, String name) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}
