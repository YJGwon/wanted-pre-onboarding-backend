package com.wanted.preonboarding.post;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import com.wanted.preonboarding.common.testbase.AcceptanceTestBase;
import com.wanted.preonboarding.post.domain.Post;
import com.wanted.preonboarding.post.dto.PostRequest;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class PostAcceptanceTest extends AcceptanceTestBase {

    private static final String BASE_URI = "/api/posts";

    @DisplayName("게시글을 작성한다.")
    @Test
    void create() {
        // given
        final String email = "test@test.com";
        final String password = "test1234";
        dataSetup.saveMember(email, password);

        // when
        final String accessToken = loginAndGetToken(email, password);
        final PostRequest request = new PostRequest("저메추", "햄버거 vs 치킨");
        final ValidatableResponse response = postWithToken(BASE_URI, request, accessToken);

        // then
        response.statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, startsWith(BASE_URI));
    }

    @DisplayName("게시글 목록을 페이지 단위로 조회한다.")
    @Test
    void findAll() {
        // given
        // 게시글 3개 저장
        final Post expected1 = dataSetup.savePost();
        final Post expected2 = dataSetup.savePost();
        dataSetup.savePost();

        // when
        final int page = 0;
        final int size = 2;
        final String path = String.format(BASE_URI + "?page=%d&size=%d", page, size);
        final ValidatableResponse response = get(path);

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("page.currentPage", equalTo(0))
                .body("page.hasNext", equalTo(true))
                .body("posts.id", contains(expected1.getId().intValue(), expected2.getId().intValue()));
    }
}
