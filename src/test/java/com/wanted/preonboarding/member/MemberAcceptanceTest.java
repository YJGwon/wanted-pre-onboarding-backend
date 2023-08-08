package com.wanted.preonboarding.member;

import com.wanted.preonboarding.common.testbase.AcceptanceTestBase;
import com.wanted.preonboarding.member.dto.MemberRequest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTestBase {

    private static final String BASE_URL = "/api/members";

    @DisplayName("이메일과 비밀번호를 입력해 회원 정보를 생성한다.")
    @Test
    void create() {
        // given & when
        final MemberRequest request = new MemberRequest("test@test.com", "test1234");
        final ValidatableResponse response = post(BASE_URL, request);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
    }
}
