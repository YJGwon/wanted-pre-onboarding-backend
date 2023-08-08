package com.wanted.preonboarding.member.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.common.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("이메일 생성")
    @Nested
    class construct {

        @DisplayName("이메일 객체를 생성한다.")
        @ParameterizedTest
        @ValueSource(strings = {"test@test.com", "test@", "@test", "@"})
        void success(final String value) {
            assertThatNoException()
                    .isThrownBy(() -> new Email(value));
        }

        @DisplayName("@가 포함되어 있지 않으면 예외가 발생한다.")
        @Test
        void throwsException_whenInvalidFormat() {
            // given
            final String invalidValue = "test";

            // when & then
            assertThatExceptionOfType(InvalidInputException.class)
                    .isThrownBy(() -> new Email(invalidValue))
                    .withMessageContaining("@를 포함");
        }
    }
}
