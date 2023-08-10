package com.wanted.preonboarding.post.service;

import com.wanted.preonboarding.post.domain.Post;
import com.wanted.preonboarding.post.dto.PostRequest;
import com.wanted.preonboarding.post.dto.PostResponse;
import com.wanted.preonboarding.post.dto.PostsResponse;
import com.wanted.preonboarding.post.exception.PostNotFoundException;
import com.wanted.preonboarding.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public PostsResponse findAll(final int page, final int size) {
        final Page<Post> found = postRepository.findAll(PageRequest.of(page, size));
        return PostsResponse.from(found);
    }

    public PostResponse findById(final Long id) {
        final Post found = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("해당 id의 게시글을 찾을 수 없습니다."));
        return PostResponse.from(found);
    }
}
