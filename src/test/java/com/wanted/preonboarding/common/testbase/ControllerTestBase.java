package com.wanted.preonboarding.common.testbase;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.member.domain.TokenProvider;
import com.wanted.preonboarding.member.service.MemberService;
import com.wanted.preonboarding.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public abstract class ControllerTestBase {

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected PostService postService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected ResultActions performGet(final String path) throws Exception {
        return mockMvc.perform(get(path)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions performPost(final String path, final Record request) throws Exception {
        return mockMvc.perform(post(path)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions performPostWithToken(final String path, final Record request) throws Exception {
        doReturn("0")
                .when(tokenProvider)
                .extractSubject("fakeToken");

        return mockMvc.perform(post(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer fakeToken")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions performPutWithToken(final String path, final Record request) throws Exception {
        doReturn("0")
                .when(tokenProvider)
                .extractSubject("fakeToken");

        return mockMvc.perform(put(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer fakeToken")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
