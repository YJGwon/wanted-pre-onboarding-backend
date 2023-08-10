package com.wanted.preonboarding.post.dto;

import com.wanted.preonboarding.post.domain.Post;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long writerId
) {

    public static PostResponse from(final Post post) {
        return new PostResponse(
                post.getId(), post.getTitle(), post.getContent(), post.getWriterId()
        );
    }
}
