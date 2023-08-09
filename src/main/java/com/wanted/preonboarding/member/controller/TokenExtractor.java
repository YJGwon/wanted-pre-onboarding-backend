package com.wanted.preonboarding.member.controller;

import com.wanted.preonboarding.member.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class TokenExtractor {

    private static final String TYPE_PREFIX = "Bearer";

    public static String extractFromRequestHeader(final HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        checkNotNull(token);
        checkType(token);
        return token.substring(TYPE_PREFIX.length() + 1);
    }

    private static void checkNotNull(final String token) {
        if (token == null) {
            throw new AuthenticationException("토큰이 존재하지 않습니다.");
        }
    }

    private static void checkType(final String token) {
        if (!token.toLowerCase().startsWith(TYPE_PREFIX.toLowerCase())) {
            throw new AuthenticationException("식별할 수 없는 형식의 토큰입니다.");
        }
    }
}
