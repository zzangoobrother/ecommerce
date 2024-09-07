package com.example.model;

public enum Status {
    WAITING("대기"),
    PROCESSING("진행중"),
    COMPLETED("완료"),
    CANCELLED("취소");

    private String display;

    Status(String display) {
        this.display = display;
    }
}
