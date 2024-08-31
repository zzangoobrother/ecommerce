package com.example.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class OrderCodeSequence {

    public static String create(LocalDateTime now) {
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + String.format("%04d", (int) (Math.random() * 9998 + 1));
    }
}
