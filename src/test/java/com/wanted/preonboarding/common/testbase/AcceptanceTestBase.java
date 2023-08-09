package com.wanted.preonboarding.common.testbase;

import com.wanted.preonboarding.common.fixture.DataSetup;
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

    protected ValidatableResponse post(final String uri, final Record body) {
        return RestAssured
                .given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(uri)
                .then().log().all();
    }
}
