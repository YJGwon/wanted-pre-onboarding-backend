package com.wanted.preonboarding.post.exception;

import com.wanted.preonboarding.common.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    private static final String TITLE = "해당하는 게시글을 찾을 수 없습니다.";

    public PostNotFoundException(final String message) {
        super(TITLE, message);
    }
}
