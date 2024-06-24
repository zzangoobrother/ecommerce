package com.example.acceptance;

import com.example.global.config.auth.AuthMemberArgumentResolver;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.CompletableFuture;

import static com.example.acceptance.OrderSteps.주문하기_요청;
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

        ExtractableResponse<Response> response = 주문하기_요청(productId, quantity);

        System.out.println(response);
    }

    @Test
    void 주문하기_2개_따로_요청() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        ExtractableResponse<Response> response = 주문하기_요청(productId, quantity);

        System.out.println(response);

        주문하기_요청(productId, quantity);
    }

    @Test
    void 주문하기_2개_동시_요청() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity)),
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity))
        ).join();
    }

    @Test
    void 주문하기_3개_동시_요청() throws Exception {
        given(authMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity)),
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity)),
                CompletableFuture.runAsync(() -> 주문하기_요청(productId, quantity))
        ).join();
    }
}
