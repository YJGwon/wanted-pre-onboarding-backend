package com.wanted.preonboarding.post.repository;

import com.wanted.preonboarding.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface PostRepository extends Repository<Post, Long> {

    Post save(Post post);

    Page<Post> findAll(Pageable pageable);
}
