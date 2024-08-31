package com.example.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class OrderSteps {

    public static ExtractableResponse<Response> 주문하기_요청(Long productId, int quantity, String loginKey, String loginValue) {
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId + "");
        params.put("quantity", quantity + "");

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(loginKey, loginValue)
                .when()
                .post("/api/v1/orders")
                .then().log().all()
                .extract();
    }
}
