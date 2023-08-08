package com.wanted.preonboarding.common.exception;

public class InvalidInputException extends ClientException {

    private static final String TITLE = "입력값이 유효하지 않습니다.";

    public InvalidInputException(final String message) {
        super(TITLE, message);
    }
}
