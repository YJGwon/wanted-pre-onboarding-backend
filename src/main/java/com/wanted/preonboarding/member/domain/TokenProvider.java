package com.wanted.preonboarding.member.domain;

public interface TokenProvider {

    String create(String subject);
}
