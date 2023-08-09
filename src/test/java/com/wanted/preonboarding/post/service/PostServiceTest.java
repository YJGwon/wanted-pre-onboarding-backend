package com.wanted.preonboarding.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.common.testbase.ServiceTestBase;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.post.dto.PostRequest;
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
}
