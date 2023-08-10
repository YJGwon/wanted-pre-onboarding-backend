package com.wanted.preonboarding.common.exception;

public class NotAllowedException extends ClientException {

    private static final String TITLE = "권한이 없습니다.";

    public NotAllowedException(final String message) {
        super(TITLE, message);
    }
}
