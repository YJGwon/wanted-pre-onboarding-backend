package com.wanted.preonboarding.post.dto;

import com.wanted.preonboarding.post.domain.Post;

public record PostSummaryResponse(Long id, String title, Long writerId) {

    public static PostSummaryResponse from(final Post post) {
        return new PostSummaryResponse(post.getId(), post.getTitle(), post.getWriterId());
    }
}
