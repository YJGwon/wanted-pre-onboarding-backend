package com.wanted.preonboarding.post.exception;

import com.wanted.preonboarding.common.exception.NotAllowedException;

public class NotWriterException extends NotAllowedException {

    private static final String MESSAGE = "게시글의 작성자가 아닙니다.";

    public NotWriterException() {
        super(MESSAGE);
    }
}
