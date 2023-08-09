package com.wanted.preonboarding.post.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.wanted.preonboarding.post.exception.InvalidPostException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PostTest {

    @DisplayName("게시글 생성")
    @Nested
    class construct {

        @DisplayName("제목이 255자를 초과하면 예외가 발생한다.")
        @Test
        void throwsException_whenTitleTooLong() {
            // given
            final String longTitle = "a".repeat(256);

            // when & then
            assertThatExceptionOfType(InvalidPostException.class)
                    .isThrownBy(() -> Post.ofNew(longTitle, "some contents....", 0L))
                    .withMessageContaining("제목", "255자 이하");
        }

        @DisplayName("본문이 65535자를 초과하면 예외가 발생한다.")
        @Test
        void throwsException_whenContentTooLong() {
            // given
            final String longContent = "a".repeat(65536);

            // when & then
            assertThatExceptionOfType(InvalidPostException.class)
                    .isThrownBy(() -> Post.ofNew("제목", longContent, 0L))
                    .withMessageContaining("본문", "65535자 이하");
        }
    }
}
