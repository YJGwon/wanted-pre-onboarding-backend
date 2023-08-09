package com.wanted.preonboarding.member.domain;

public interface TokenProvider {

    String create(String subject);

    void validate(String token);

    String extractSubject(String token);
}
