package com.example.acceptance;

import com.example.acceptance.utils.DataLoader;
import com.example.acceptance.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@ActiveProfiles("test")
@SpringBootTest
public class AcceptanceTest {

//    @LocalServerPort
//    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DataLoader dataLoader;

    protected String loginKey = "JSESSIONID";
    protected String loginValue;

    @BeforeEach
    public void setUp() {
        RestAssured.port = 8083;
        databaseCleanup.execute();
        dataLoader.loadData();

        ExtractableResponse<Response> response = MemberSteps.로그인_요청("abcde", "1234");
        loginValue = response.cookie(loginKey);

        ProductSteps.상풍_등록_요청("빈티지 티", new BigDecimal("1000"), 100, loginKey, loginValue);
    }
}
