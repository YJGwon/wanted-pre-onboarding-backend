package com.wanted.preonboarding.member.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.wanted.preonboarding.member.exception.AuthenticationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MemberControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(final AuthenticationException e) {
        return ErrorResponse.builder(e, UNAUTHORIZED, e.getMessage())
                .title(e.getTitle())
                .build();
    }
}
