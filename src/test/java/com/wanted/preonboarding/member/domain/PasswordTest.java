package com.wanted.preonboarding.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.wanted.preonboarding.common.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PasswordTest {

    @DisplayName("평문 비밀번호 암호화")
    @Nested
    class fromPlainText {

        @DisplayName("평문 비밀번호를 암호화한다.")
        @Test
        void success() {
            // given
            final String plainText = "test1234";

            // when
            final Password encodedPassword = Password.fromPlainText(plainText);

            // then
            assertThat(encodedPassword.isSamePassword(plainText)).isTrue();
        }

        @DisplayName("비밀번호의 길이가 8자 미만일 경우 예외가 발생한다.")
        @Test
        void throwsException_whenShorterThanMinLength() {
            // given
            final String shortText = "test123";

            // when & then
            assertThatExceptionOfType(InvalidInputException.class)
                    .isThrownBy(() -> Password.fromPlainText(shortText))
                    .withMessageContaining("8자 이상");
        }
    }
}
