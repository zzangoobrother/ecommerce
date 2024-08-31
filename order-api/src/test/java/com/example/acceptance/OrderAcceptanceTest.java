package com.example.acceptance;

import com.example.global.config.auth.AuthMemberArgumentResolver;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.CompletableFuture;

import static com.example.acceptance.OrderSteps.주문하기_요청;
import static com.example.acceptance.ProductSteps.상품_단건_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class OrderAcceptanceTest extends AcceptanceTest {

    private static final Long productId = 1L;
    private static final int quantity = 10;

    @MockBean
    private AuthMemberArgumentResolver authMemberArgumentResolver;

    @Test
    void 주문하기() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        주문하기_요청(productId, quantity, loginKey, loginValue);

        Thread.sleep(1000);

        ExtractableResponse<Response> response = 상품_단건_조회_요청(productId, loginKey, loginValue);

        assertAll(
                () -> assertThat(response.jsonPath().getLong("productId")).isEqualTo(productId),
                () -> assertThat(response.jsonPath().getLong("quantity")).isEqualTo(90)
        );
    }

    @Test
    void 주문하기_2개_따로_요청() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        주문하기_요청(productId, quantity, loginKey, loginValue);

        주문하기_요청(productId, quantity, loginKey, loginValue);

        Thread.sleep(1000);

        ExtractableResponse<Response> response = 상품_단건_조회_요청(productId, loginKey, loginValue);

        assertAll(
                () -> assertThat(response.jsonPath().getLong("productId")).isEqualTo(productId),
                () -> assertThat(response.jsonPath().getLong("quantity")).isEqualTo(80)
        );
    }

    @Test
    void 주문하기_2개_동시_요청() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity, loginKey, loginValue)),
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity, loginKey, loginValue))
        ).join();

        Thread.sleep(1000);

        ExtractableResponse<Response> response = 상품_단건_조회_요청(productId, loginKey, loginValue);

        assertAll(
                () -> assertThat(response.jsonPath().getLong("productId")).isEqualTo(productId),
                () -> assertThat(response.jsonPath().getLong("quantity")).isEqualTo(80)
        );
    }

    @Test
    void 주문하기_3개_동시_요청() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity, loginKey, loginValue)),
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity, loginKey, loginValue)),
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity, loginKey, loginValue))
        ).join();

        Thread.sleep(1000);

        ExtractableResponse<Response> response = 상품_단건_조회_요청(productId, loginKey, loginValue);

        assertAll(
                () -> assertThat(response.jsonPath().getLong("productId")).isEqualTo(productId),
                () -> assertThat(response.jsonPath().getLong("quantity")).isEqualTo(70)
        );
    }
}
