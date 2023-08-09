package com.wanted.preonboarding.post.service;

import com.wanted.preonboarding.post.domain.Post;
import com.wanted.preonboarding.post.dto.PostRequest;
import com.wanted.preonboarding.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long create(final PostRequest postRequest, final Long writerId) {
        final Post post = Post.ofNew(postRequest.title(), postRequest.content(), writerId);
        postRepository.save(post);

        return post.getId();
    }
}
