package com.wanted.preonboarding.common.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.wanted.preonboarding.common.exception.ClientException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ErrorResponse handleClientException(final ClientException e) {
        return ErrorResponse.builder(e, BAD_REQUEST, e.getMessage())
                .title(e.getTitle())
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ErrorResponse handleRequestException(final Exception e) {
        final String message = extractMessage(e);
        return ErrorResponse.builder(e, BAD_REQUEST, message)
                .title("요청 본문이 올바르지 않습니다.")
                .build();
    }

    @ExceptionHandler
    public ErrorResponse handleError(final Exception e) {
        final String message = extractMessage(e);
        return ErrorResponse.builder(e, INTERNAL_SERVER_ERROR, message)
                .title("서버에 에러가 발생했습니다.")
                .build();
    }

    private String extractMessage(final Exception e) {
        if (Objects.requireNonNull(e) instanceof MethodArgumentNotValidException m) {
            return m.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        }
        return e.getMessage();
    }
}
