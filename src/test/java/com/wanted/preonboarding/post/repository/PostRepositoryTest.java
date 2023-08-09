package com.wanted.preonboarding.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.repository.MemberRepository;
import com.wanted.preonboarding.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
