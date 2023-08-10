package com.wanted.preonboarding.common.fixture;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.repository.MemberRepository;
import com.wanted.preonboarding.post.domain.Post;
import com.wanted.preonboarding.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = REQUIRES_NEW)
public class DataSetup {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    public Member saveMember(final String email, final String password) {
        final Member member = Member.ofNew(email, password);
        return memberRepository.save(member);
    }

    public Post savePost() {
        final Member writer = saveMember("writer@test.com", "test1234");
        return savePost(writer);
    }

    public Post savePost(final Member writer) {
        final Post post = Post.ofNew("some title", "some contents...", writer.getId());
        return postRepository.save(post);
    }
}
