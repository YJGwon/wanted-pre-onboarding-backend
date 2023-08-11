package com.wanted.preonboarding.post;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import com.wanted.preonboarding.common.testbase.AcceptanceTestBase;
import com.wanted.preonboarding.member.domain.Member;
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
                .body("posts.id", contains(expected1.getId().intValue(), expected2.getId().intValue()));
    }

    @DisplayName("특정 게시글을 id로 조회한다.")
    @Test
    void findById() {
        // given
        final Post expected = dataSetup.savePost();

        // when
        final String path = BASE_URI + "/" + expected.getId();
        final ValidatableResponse response = get(path);

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("id", equalTo(expected.getId().intValue()));
    }

    @DisplayName("id에 해당하는 특정 게시글을 수정한다.")
    @Test
    void modifyById() {
        // given
        final String email = "writer@foo.bar";
        final String password = "write1234";
        final Member writer = dataSetup.saveMember(email, password);
        final String token = loginAndGetToken(email, password);

        final Post savedPost = dataSetup.savePost(writer);

        // when
        final String path = BASE_URI + "/" + savedPost.getId();
        final PostRequest request = new PostRequest("수정할 제목", "수정할 본문");
        final ValidatableResponse response = putWithToken(path, request, token);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("id에 해당하는 특정 게시글을 삭제한다.")
    @Test
    void deleteById() {
        // given
        final String email = "writer@foo.bar";
        final String password = "write1234";
        final Member writer = dataSetup.saveMember(email, password);
        final String token = loginAndGetToken(email, password);

        final Post savedPost = dataSetup.savePost(writer);

        // when
        final String path = BASE_URI + "/" + savedPost.getId();
        final ValidatableResponse response = deleteWithToken(path, token);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
    }
}
