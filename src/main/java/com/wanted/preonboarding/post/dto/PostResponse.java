package com.wanted.preonboarding.post.dto;

import com.wanted.preonboarding.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostResponse(
        @Schema(description = "게시글 id", example = "1")
        Long id,
        @Schema(description = "제목", example = "안녕하세요")
        String title,
        @Schema(description = "본문", example = "이 편지는 영국으로부터 시작되어...")
        String content,
        @Schema(description = "작성자 id", example = "1")
        Long writerId
) {

    public static PostResponse from(final Post post) {
        return new PostResponse(
                post.getId(), post.getTitle(), post.getContent(), post.getWriterId()
        );
    }
}
