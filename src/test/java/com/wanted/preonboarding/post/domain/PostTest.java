package com.wanted.preonboarding.post.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.post.exception.InvalidPostException;
import com.wanted.preonboarding.post.exception.NotWriterException;
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

    @DisplayName("제목을 변경할 때, 255자를 초과하면 예외가 발생한다.")
    @Test
    void changeTitle_throwsException_whenTitleTooLong() {
        // given
        final Post post = Post.ofNew("아주 멋진 제목", "그에 걸맞는 아주아주 멋진 내용", 0L);
        final String longTitle = "a".repeat(256);

        // when & then
        assertThatExceptionOfType(InvalidPostException.class)
                .isThrownBy(() -> post.changeTitle(longTitle))
                .withMessageContaining("제목", "255자 이하");
    }

    @DisplayName("본문을 변경할 때, 65535자를 초과하면 예외가 발생한다.")
    @Test
    void changeContent_throwsException_whenContentTooLong() {
        // given
        final Post post = Post.ofNew("아주 멋진 제목", "그에 걸맞는 아주아주 멋진 내용", 0L);
        final String longContent = "a".repeat(65536);

        // when & then
        assertThatExceptionOfType(InvalidPostException.class)
                .isThrownBy(() -> post.changeContent(longContent))
                .withMessageContaining("본문", "65535자 이하");
    }

    @DisplayName("게시글 작성자 검증")
    @Nested
    class validateWriter {

        @DisplayName("게시글의 작성자인지 검증한다.")
        @Test
        void success() {
            // given
            final long writerId = 1L;
            final Post post = Post.ofNew("아주 멋진 제목", "그에 걸맞는 아주아주 멋진 내용", writerId);

            // when & then
            assertThatNoException()
                    .isThrownBy(() -> post.validateWriter(writerId));
        }

        @DisplayName("작성자가 아니면 예외가 발생한다.")
        @Test
        void throwsException_whenNotWriter() {
            // given
            final long notWriterId = 0L;
            final Post post = Post.ofNew("아주 멋진 제목", "그에 걸맞는 아주아주 멋진 내용", 1L);

            // when & then
            assertThatExceptionOfType(NotWriterException.class)
                    .isThrownBy(() -> post.validateWriter(notWriterId));
        }
    }
}
