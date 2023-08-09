package com.wanted.preonboarding.post.dto;

import com.wanted.preonboarding.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

public record PostRequest(
        @Schema(description = "제목", defaultValue = "안녕하세요")
        @Length(max = Post.MAX_TITLE_LENGTH, message = Post.TOO_LONG_TITLE_MESSAGE)
        String title,
        @Schema(description = "본문", defaultValue = "이 편지는 영국으로부터 시작되어...")
        @Length(max = Post.MAX_CONTENT_LENGTH, message = Post.TOO_LONG_CONTENT_MESSAGE)
        String content
) {
}
