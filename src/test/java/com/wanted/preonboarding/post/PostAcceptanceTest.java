package com.wanted.preonboarding.post;

import static org.hamcrest.Matchers.startsWith;

import com.wanted.preonboarding.common.testbase.AcceptanceTestBase;
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
}
