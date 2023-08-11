package com.wanted.preonboarding.post.controller;

import com.wanted.preonboarding.member.annotation.Authentication;
import com.wanted.preonboarding.member.annotation.AuthenticationPrincipal;
import com.wanted.preonboarding.post.dto.PostRequest;
import com.wanted.preonboarding.post.dto.PostResponse;
import com.wanted.preonboarding.post.dto.PostsResponse;
import com.wanted.preonboarding.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Posts", description = "게시글 api")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "20";

    private static final String PAGE_UNDER_ZERO_MESSAGE = "페이지 번호는 0 이상이어야 합니다.";
    private static final String SIZE_UNDER_ONE_MESSAGE = "페이지 크기는 1 이상이어야 합니다.";

    private final PostService postService;

    @Authentication
    @Operation(summary = "게시글 생성")
    @ApiResponse(
            responseCode = "400", description = "제목 또는 본문 최대 길이 초과",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody @Valid final PostRequest request,
                                       @AuthenticationPrincipal final Long writerId) {
        final Long postId = postService.create(request, writerId);
        final URI location = URI.create("/api/posts/" + postId);
        return ResponseEntity.created(location)
                .build();
    }

    @Operation(summary = "게시글 목록 조회")
    @ApiResponse(
            responseCode = "400", description = "페이지가 음수이거나 size가 1 미만",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @GetMapping
    public PostsResponse findAll(@Parameter(description = "페이지 번호(0부터 시작), 기본값 0")
                                 @RequestParam(required = false, defaultValue = DEFAULT_PAGE)
                                 @PositiveOrZero(message = PAGE_UNDER_ZERO_MESSAGE) final int page,
                                 @Parameter(description = "페이지 크기, 기본값 20")
                                 @RequestParam(required = false, defaultValue = DEFAULT_SIZE)
                                 @Positive(message = SIZE_UNDER_ONE_MESSAGE) final int size) {
        return postService.findAll(page, size);
    }

    @Operation(summary = "특정 게시글 조회")
    @GetMapping("/{postId}")
    public PostResponse findById(@PathVariable final Long postId) {
        return postService.findById(postId);
    }

    @Authentication
    @Operation(summary = "특정 게시글 수정")
    @ApiResponse(
            responseCode = "400", description = "제목 또는 본문 최대 길이 초과",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @ApiResponse(
            responseCode = "403", description = "작성자 아님",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyById(@PathVariable final Long postId,
                           @AuthenticationPrincipal final Long writerId,
                           @RequestBody @Valid final PostRequest request) {
        postService.modifyById(postId, writerId, request);
    }

    @Authentication
    @Operation(summary = "특정 게시글 삭제")
    @ApiResponse(
            responseCode = "403", description = "작성자 아님",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable final Long postId,
                           @AuthenticationPrincipal final Long writerId) {
        postService.deleteById(postId, writerId);
    }
}
