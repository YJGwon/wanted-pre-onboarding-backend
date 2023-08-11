package com.wanted.preonboarding.post.dto;

import com.wanted.preonboarding.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostSummaryResponse(
        @Schema(description = "게시글 id", example = "1")
        Long id,
        @Schema(description = "제목", example = "안녕하세요")
        String title,
        @Schema(description = "작성자 id", example = "1")
        Long writerId
) {

    public static PostSummaryResponse from(final Post post) {
        return new PostSummaryResponse(post.getId(), post.getTitle(), post.getWriterId());
    }
}
