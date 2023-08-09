package com.wanted.preonboarding.member;

import static org.hamcrest.Matchers.notNullValue;

import com.wanted.preonboarding.common.testbase.AcceptanceTestBase;
import com.wanted.preonboarding.member.dto.LoginRequest;
import com.wanted.preonboarding.member.dto.MemberRequest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTestBase {

    private static final String BASE_URI = "/api/members";

    @DisplayName("이메일과 비밀번호를 입력해 회원 정보를 생성한다.")
    @Test
    void create() {
        // given & when
        final MemberRequest request = new MemberRequest("test@test.com", "test1234");
        final ValidatableResponse response = post(BASE_URI, request);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("이메일과 비밀번호를 입력해 로그인하고 토큰을 발급받는다.")
    @Test
    void login() {
        // given
        final String email = "test@test.com";
        final String password = "test1234";
        dataSetup.saveMember(email, password);

        // when
        final LoginRequest request = new LoginRequest(email, password);
        final ValidatableResponse response = post(BASE_URI + "/login", request);

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("accessToken", notNullValue());
    }
}
