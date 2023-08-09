package com.wanted.preonboarding.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.common.testbase.ServiceTestBase;
import com.wanted.preonboarding.member.dto.LoginRequest;
import com.wanted.preonboarding.member.dto.LoginResponse;
import com.wanted.preonboarding.member.dto.MemberRequest;
import com.wanted.preonboarding.member.exception.LoginFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberServiceTest extends ServiceTestBase {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원 정보를 생성하여 저장한다.")
    @Test
    void create() {
        // given
        final MemberRequest request = new MemberRequest("test@test.com", "test1234");

        // when & then
        assertThatNoException()
                .isThrownBy(() -> memberService.create(request));
    }

    @DisplayName("로그인")
    @Nested
    class login {

        @DisplayName("이메일과 비밀번호로 로그인하고 토큰을 발급받는다.")
        @Test
        void success() {
            // given
            final String email = "test@test.com";
            final String password = "test1234";
            dataSetup.saveMember(email, password);

            // when
            final LoginRequest request = new LoginRequest(email, password);
            final LoginResponse response = memberService.login(request);

            // then
            final String token = response.accessToken();
            assertThat(token.split(".")).allSatisfy((part) -> assertThat(part).isBase64());
        }

        @DisplayName("존재하지 않는 이메일로 로그인하면 예외가 발생한다.")
        @Test
        void throwsException_whenEmailWrong() {
            // given
            final String wrongEmail = "wrong@test.com";
            final String password = "test1234";
            dataSetup.saveMember("test@test.com", password);

            // when & then
            final LoginRequest request = new LoginRequest(wrongEmail, password);
            assertThatExceptionOfType(LoginFailedException.class)
                    .isThrownBy(() -> memberService.login(request));
        }
    }
}
