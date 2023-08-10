package com.wanted.preonboarding.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.common.testbase.ServiceTestBase;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.post.domain.Post;
import com.wanted.preonboarding.post.dto.PostRequest;
import com.wanted.preonboarding.post.dto.PostSummaryResponse;
import com.wanted.preonboarding.post.dto.PostsResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostServiceTest extends ServiceTestBase {

    @Autowired
    private PostService postService;

    @DisplayName("새 게시글을 생성하여 저장하고 id를 반환한다.")
    @Test
    void create() {
        // given
        final Member writer = dataSetup.saveMember("test@test.com", "test1234");
        final PostRequest request = new PostRequest("some title...", "some content...");

        // when
        final Long postId = postService.create(request, writer.getId());

        // then
        assertThat(postId).isNotNull();
    }

    @DisplayName("게시글 목록을 페이지 단위로 조회한다.")
    @Test
    void findAll() {
        // given
        // 게시글 3개 저장
        dataSetup.savePost();
        dataSetup.savePost();
        final Post expected = dataSetup.savePost();

        // when
        final PostsResponse response = postService.findAll(1, 2);

        // then
        final List<Long> foundIds = response.posts()
                .stream()
                .map(PostSummaryResponse::id)
                .toList();
        assertThat(foundIds).containsOnly(expected.getId());
    }
}
