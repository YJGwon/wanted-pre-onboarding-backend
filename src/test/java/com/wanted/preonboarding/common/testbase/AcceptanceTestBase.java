package com.wanted.preonboarding.common.testbase;

import com.wanted.preonboarding.common.fixture.DataSetup;
import com.wanted.preonboarding.member.dto.LoginRequest;
import com.wanted.preonboarding.member.dto.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AcceptanceTestBase {

    @LocalServerPort
    private int port;

    @Autowired
    protected DataSetup dataSetup;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    protected ValidatableResponse get(final String path) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all();
    }

    protected ValidatableResponse post(final String path, final Record body) {
        return RestAssured
                .given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all();
    }

    protected ValidatableResponse postWithToken(final String path, final Record body, final String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all();
    }

    protected ValidatableResponse putWithToken(final String path, final Record body, final String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put(path)
                .then().log().all();
    }

    protected String loginAndGetToken(final String email, final String password) {
        final LoginRequest request = new LoginRequest(email, password);
        return post("/api/members/login", request)
                .extract()
                .body()
                .as(LoginResponse.class)
                .accessToken();
    }
}
