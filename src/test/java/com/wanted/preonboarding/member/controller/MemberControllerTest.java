package com.wanted.preonboarding.member.controller;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.preonboarding.common.testbase.ControllerTestBase;
import com.wanted.preonboarding.member.dto.LoginRequest;
import com.wanted.preonboarding.member.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class MemberControllerTest extends ControllerTestBase {

    private static final String BASE_URI = "/api/members";

    @DisplayName("회원 가입")
    @Nested
    class signup {

        @DisplayName("이메일에 @가 포함되어 있지 않으면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenEmailInvalid() throws Exception {
            // given
            final String invalidEmail = "invalid";

            // when
            final MemberRequest request = new MemberRequest(invalidEmail, "test1234");
            final ResultActions resultActions = performPost(BASE_URI, request);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 본문이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("이메일", "@를 포함")));
        }

        @DisplayName("비밀번호가 8자 미만이면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenPasswordTooShort() throws Exception {
            // given
            final String shortPassword = "test123";

            // when
            final MemberRequest request = new MemberRequest("test@test.com", shortPassword);
            final ResultActions resultActions = performPost(BASE_URI, request);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 본문이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("비밀번호", "8자 이상")));
        }
    }

    @DisplayName("로그인")
    @Nested
    class login {

        private static final String LOGIN_URI = BASE_URI + "/login";

        @DisplayName("이메일에 @가 포함되어 있지 않으면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenEmailInvalid() throws Exception {
            // given
            final String invalidEmail = "invalid";

            // when
            final LoginRequest request = new LoginRequest(invalidEmail, "test1234");
            final ResultActions resultActions = performPost(LOGIN_URI, request);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 본문이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("이메일", "@를 포함")));
        }

        @DisplayName("비밀번호가 8자 미만이면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenPasswordTooShort() throws Exception {
            // given
            final String shortPassword = "test123";

            // when
            final LoginRequest request = new LoginRequest("test@test.com", shortPassword);
            final ResultActions resultActions = performPost(LOGIN_URI, request);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 본문이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("비밀번호", "8자 이상")));
        }
    }
}
