package com.example.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MemberSteps {

    public static ExtractableResponse<Response> 회원가입_요청(String loginId, String password, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("loginId", loginId);
        params.put("password", password);
        params.put("name", name);

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/v1/members/signup")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인_요청(String loginId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("loginId", loginId);
        params.put("password", password);

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/v1/members/login")
                .then().log().all()
                .extract();
    }
}
