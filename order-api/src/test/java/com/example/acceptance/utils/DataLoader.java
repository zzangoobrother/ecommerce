package com.example.acceptance.utils;

import com.example.acceptance.MemberSteps;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {


    public DataLoader() {

    }

    public void loadData() {
        MemberSteps.회원가입_요청("abcde", "1234", "홍길동");
    }
}
