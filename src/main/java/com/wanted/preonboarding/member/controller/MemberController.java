package com.wanted.preonboarding.member.controller;

import com.wanted.preonboarding.member.dto.LoginRequest;
import com.wanted.preonboarding.member.dto.LoginResponse;
import com.wanted.preonboarding.member.dto.MemberRequest;
import com.wanted.preonboarding.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Members", description = "회원 api")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 회원가입")
    @ApiResponse(
            responseCode = "400", description = "유효하지 않은 이메일 또는 비밀번호",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody @Valid MemberRequest request) {
        memberService.create(request);
    }

    @Operation(summary = "사용자 로그인")
    @ApiResponse(
            responseCode = "400", description = "유효하지 않거나 잘못된 이메일 또는 비밀번호",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return memberService.login(request);
    }
}
