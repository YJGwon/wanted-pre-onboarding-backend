package com.wanted.preonboarding.member.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.member.exception.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JwtTokenProvider.class)
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("JWT token을 생성한다.")
    @Test
    void create() {
        // given & when
        final String token = jwtTokenProvider.create("someSubject");

        // then
        assertThat(token.split(".")).allSatisfy(part -> assertThat(part).isBase64());
    }

    @DisplayName("토큰 검증")
    @Nested
    class validate {

        @DisplayName("올바르게 서명된 JWT 토큰인지 검증한다.")
        @Test
        void success() {
            // given
            final String token = jwtTokenProvider.create("someSubject");

            // when & than
            assertThatNoException()
                    .isThrownBy(() -> jwtTokenProvider.validate(token));
        }

        @DisplayName("유효하지 않은 토큰이면 예외가 발생한다.")
        @Test
        void throwsException_whenTokenInvalid() {
            // given
            final String invalidToken = "invalid.token.provided";

            // when & than
            assertThatExceptionOfType(AuthenticationException.class)
                    .isThrownBy(() -> jwtTokenProvider.validate(invalidToken))
                    .withMessageContaining("유효하지 않은 토큰");
        }
    }

    @DisplayName("토큰에서 subject를 추출한다.")
    @Test
    void extractSubject() {
        // given
        final String expected = "anySubject";
        final String token = jwtTokenProvider.create(expected);

        // when
        final String extractedSubject = jwtTokenProvider.extractSubject(token);

        // then
        assertThat(extractedSubject).isEqualTo(expected);
    }
}
