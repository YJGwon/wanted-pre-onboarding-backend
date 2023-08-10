package com.wanted.preonboarding.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.common.testbase.ServiceTestBase;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.post.domain.Post;
import com.wanted.preonboarding.post.dto.PostRequest;
import com.wanted.preonboarding.post.dto.PostResponse;
import com.wanted.preonboarding.post.dto.PostSummaryResponse;
import com.wanted.preonboarding.post.dto.PostsResponse;
import com.wanted.preonboarding.post.exception.PostNotFoundException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @DisplayName("특정 게시글 조회")
    @Nested
    class findById {

        @DisplayName("id로 특정 게시글을 조회한다.")
        @Test
        void success() {
            // given
            final Post expected = dataSetup.savePost();

            // when
            final PostResponse response = postService.findById(expected.getId());

            // then
            assertThat(response.id()).isEqualTo(expected.getId());
        }

        @DisplayName("id에 해당하는 게시글이 없으면 예외가 발생한다.")
        @Test
        void throwsException_whenPostNotFound() {
            // given
            final long fakeId = 0L;

            // when & then
            assertThatExceptionOfType(PostNotFoundException.class)
                    .isThrownBy(() -> postService.findById(fakeId))
                    .withMessageContaining("id");
        }
    }

    @DisplayName("특정 게시글 수정")
    @Nested
    class modifyById {

        @DisplayName("id에 해당하는 특정 게시글을 수정한다.")
        @Test
        void success() {
            // given
            final Member writer = dataSetup.saveMember("foo@bar.com", "test1234");
            final Post savedPost = dataSetup.savePost(writer);

            // when & then
            final PostRequest request = new PostRequest("(수정) 수정된 제목", "이건 수정된 본문");
            assertThatNoException()
                    .isThrownBy(() -> postService.modifyById(savedPost.getId(), writer.getId(), request));
        }

        @DisplayName("id에 해당하는 게시글이 없으면 예외가 발생한다.")
        @Test
        void throwsException_whenPostNotFound() {
            // given
            final Member member = dataSetup.saveMember("foo@bar.com", "test1234");
            final long fakeId = 0L;

            // when & then
            final PostRequest request = new PostRequest("(수정) 수정된 제목", "이건 수정된 본문");
            assertThatExceptionOfType(PostNotFoundException.class)
                    .isThrownBy(() -> postService.modifyById(fakeId, member.getId(), request))
                    .withMessageContaining("id");
        }
    }

    @DisplayName("특정 게시글 삭제")
    @Nested
    class deleteById {

        @DisplayName("id에 해당하는 특정 게시글을 삭제한다.")
        @Test
        void success() {
            // given
            final Member writer = dataSetup.saveMember("foo@bar.com", "test1234");
            final Post savedPost = dataSetup.savePost(writer);

            // when & then
            assertThatNoException()
                    .isThrownBy(() -> postService.deleteById(savedPost.getId(), writer.getId()));
        }

        @DisplayName("id에 해당하는 게시글이 없으면 예외가 발생한다.")
        @Test
        void throwsException_whenPostNotFound() {
            // given
            final Member member = dataSetup.saveMember("foo@bar.com", "test1234");
            final long fakeId = 0L;

            // when & then
            assertThatExceptionOfType(PostNotFoundException.class)
                    .isThrownBy(() -> postService.deleteById(fakeId, member.getId()))
                    .withMessageContaining("id");
        }
    }
}
