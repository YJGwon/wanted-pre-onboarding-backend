package com.wanted.preonboarding.post.exception;

import com.wanted.preonboarding.common.exception.ClientException;

public class InvalidPostException extends ClientException {

    private static final String TITLE = "올바르지 않은 게시글입니다.";

    public InvalidPostException(final String message) {
        super(TITLE, message);
    }
}
