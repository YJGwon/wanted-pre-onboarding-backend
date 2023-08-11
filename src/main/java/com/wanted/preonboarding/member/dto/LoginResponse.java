package com.wanted.preonboarding.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "access token")
        String accessToken
) {
}
