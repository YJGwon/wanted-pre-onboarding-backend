package com.wanted.preonboarding.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.repository.MemberRepository;
import com.wanted.preonboarding.post.domain.Post;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("게시글을 저장한다.")
    @Test
    void save() {
        // given
        final Member writer = memberRepository.save(Member.ofNew("test@test.com", "test1234"));
        final Post post = Post.ofNew("title", "some contents...", writer.getId());

        // when
        final Post savedPost = postRepository.save(post);

        // then
        assertThat(savedPost.getId()).isNotNull();
    }

    @DisplayName("게시글 목록을 페이지 단위로 조회한다.")
    @Test
    void findAll() {
        // given
        final Member writer = memberRepository.save(Member.ofNew("test@test.com", "test1234"));
        // 게시글 3개 저장
        postRepository.save(Post.ofNew("title1", "some contents...", writer.getId()));
        postRepository.save(Post.ofNew("title2", "some contents...", writer.getId()));
        final Post expected = postRepository.save(Post.ofNew("title3", "some contents...", writer.getId()));

        // when
        final Page<Post> found = postRepository.findAll(PageRequest.of(1, 2));

        // then
        final List<Long> foundIds = found.getContent()
                .stream()
                .map(Post::getId)
                .toList();
        assertThat(foundIds).containsOnly(expected.getId());
    }
}
