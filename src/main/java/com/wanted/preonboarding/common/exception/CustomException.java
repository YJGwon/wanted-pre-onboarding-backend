package com.wanted.preonboarding.common.exception;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    private final String title;

    public CustomException(final String title, final String message) {
        super(message);
        this.title = title;
    }
}
