package com.wanted.preonboarding.post.controller;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.preonboarding.common.testbase.ControllerTestBase;
import com.wanted.preonboarding.post.dto.PostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class PostControllerTest extends ControllerTestBase {

    private static final String BASE_URI = "/api/posts";

    @DisplayName("게시글 작성")
    @Nested
    class create {

        @DisplayName("제목이 255자를 초과하면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenTitleTooLong() throws Exception {
            // given
            final String longTitle = "a".repeat(256);

            // when
            final PostRequest request = new PostRequest(longTitle, "some content...");
            final ResultActions resultActions = performPostWithToken(BASE_URI, request);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 본문이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("게시글 제목", "255자 이하")));
        }

        @DisplayName("본문이 65535자를 초과하면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenContentTooLong() throws Exception {
            // given
            final String longContent = "a".repeat(65536);

            // when
            final PostRequest request = new PostRequest("some title", longContent);
            final ResultActions resultActions = performPostWithToken(BASE_URI, request);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 본문이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("게시글 본문", "65535자 이하")));
        }
    }

    @DisplayName("게시글 목록 페이지 단위 조회")
    @Nested
    class findAll {

        private static final String PAGE_PARAM_FORMAT = "?page=%d&size=%d";

        @DisplayName("페이지 번호가 0보다 작으면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenPageIsUnderZero() throws Exception {
            // given
            final int pageUnderZero = -1;

            // when
            final String params = String.format(PAGE_PARAM_FORMAT, pageUnderZero, 1);
            final ResultActions resultActions = performGet(BASE_URI + params);

            // then
            resultActions.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 파라미터값이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("페이지 번호", "0 이상")));
        }

        @DisplayName("페이지 크기가 1보다 작으면 Bad Request를 응답한다.")
        @Test
        void returnsBadRequest_whenSizeIsUnderOne() throws Exception {
            // given
            final int sizeUnderOne = 0;

            // when
            final String params = String.format(PAGE_PARAM_FORMAT, 0, sizeUnderOne);
            final ResultActions resultActions = performGet(BASE_URI + params);

            // then
            resultActions.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("title").value("요청 파라미터값이 올바르지 않습니다."))
                    .andExpect(jsonPath("detail", stringContainsInOrder("페이지 크기", "1 이상")));
        }
    }
}
