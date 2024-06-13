package com.example.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String name;

    @Builder
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
