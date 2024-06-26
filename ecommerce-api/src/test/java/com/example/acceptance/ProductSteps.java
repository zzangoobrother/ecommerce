package com.example.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ProductSteps {

    public static ExtractableResponse<Response> 상품_단건_조회_요청(Long productId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products/" + productId)
                .then().log().all()
                .extract();
    }
}
