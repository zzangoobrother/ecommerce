package com.example.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductSteps {

    public static ExtractableResponse<Response> 상품_단건_조회_요청(Long productId, String loginKey, String loginValue) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(loginKey, loginValue)
                .when()
                .get("/api/v1/products/" + productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상풍_등록_요청(String name, BigDecimal price, int quantity, String loginKey, String loginValue) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("price", price.toString());
        params.put("quantity", quantity + "");

        return RestAssured.given().log().all()
                .body(params)
                .cookie(loginKey, loginValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/v1/products")
                .then().log().all()
                .extract();
    }
}
