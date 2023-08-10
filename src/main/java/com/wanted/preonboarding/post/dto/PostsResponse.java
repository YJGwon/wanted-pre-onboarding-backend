package com.wanted.preonboarding.post.dto;

import com.wanted.preonboarding.post.domain.Post;
import java.util.List;
import org.springframework.data.domain.Page;

public record PostsResponse(PageResponse page, List<PostSummaryResponse> posts) {

    public static PostsResponse from(final Page<Post> postPage) {
        final List<PostSummaryResponse> postSummaryResponses = postPage.getContent().stream()
                .map(PostSummaryResponse::from)
                .toList();
        return new PostsResponse(PageResponse.from(postPage), postSummaryResponses);
    }
}
