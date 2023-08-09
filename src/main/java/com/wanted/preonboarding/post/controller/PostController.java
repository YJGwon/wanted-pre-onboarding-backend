package com.wanted.preonboarding.post.controller;

import com.wanted.preonboarding.member.annotation.AuthenticationPrincipal;
import com.wanted.preonboarding.post.dto.PostRequest;
import com.wanted.preonboarding.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성")
    @ApiResponse(
            responseCode = "400", description = "제목 또는 본문 최대 길이 초과",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final PostRequest request,
                                       @AuthenticationPrincipal final Long writerId) {
        final Long postId = postService.create(request, writerId);
        final URI location = URI.create("/api/posts/" + postId);
        return ResponseEntity.created(location)
                .build();
    }
}
