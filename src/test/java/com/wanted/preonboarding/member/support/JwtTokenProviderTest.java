package com.wanted.preonboarding.member.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JwtTokenProvider.class)
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void create() {
        // given & when
        final String token = jwtTokenProvider.create("someSubject");

        // then
        assertThat(token.split(".")).allSatisfy(part -> assertThat(part).isBase64());
    }
}
