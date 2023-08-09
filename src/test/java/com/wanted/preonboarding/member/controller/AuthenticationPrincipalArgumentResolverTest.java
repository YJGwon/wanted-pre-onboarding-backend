package com.wanted.preonboarding.member.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.preonboarding.member.controller.testcontroller.AuthTestController;
import com.wanted.preonboarding.member.domain.TokenProvider;
import com.wanted.preonboarding.member.support.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = AuthTestController.class)
@Import(JwtTokenProvider.class)
public class AuthenticationPrincipalArgumentResolverTest {

    private static final String PREFIX_BEARER = "Bearer ";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("사용자 식별 정보가 필요한 요청에 대해 token에서 사용자 id를 추출한다.")
    @Test
    void resolve_whenPrincipalRequired() throws Exception {
        // given
        final long id = 1L;
        final String validToken = tokenProvider.create(Long.toString(id));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/resolve-principal")
                        .header(HttpHeaders.AUTHORIZATION, PREFIX_BEARER + validToken))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("memberId").value(1));
    }
}
