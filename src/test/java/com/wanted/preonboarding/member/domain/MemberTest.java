package com.wanted.preonboarding.member.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.member.exception.LoginFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("비밀번호 검사")
    @Nested
    class checkPassword {

        @DisplayName("비밀번호가 같은지 검사한다.")
        @Test
        void success() {
            // given
            final String password = "test1234";
            final Member member = Member.ofNew("test@test.com", password);

            // when & then
            assertThatNoException()
                    .isThrownBy(() -> member.checkPassword(password));
        }

        @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
        @Test
        void throwsException_whenWrongPassword() {
            // given
            final String wrongPassword = "wrong1234";
            final Member member = Member.ofNew("test@test.com", "test1234");

            // when & then
            assertThatExceptionOfType(LoginFailedException.class)
                    .isThrownBy(() -> member.checkPassword(wrongPassword));
        }
    }
}
