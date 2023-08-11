package com.wanted.preonboarding.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

public record PageResponse(
        @Schema(description = "페이지 크기", example = "20")
        int size,
        @Schema(description = "전체 페이지 수", example = "3")
        int totalPages,
        @Schema(description = "현재 페이지", example = "0")
        int currentPage
) {

    public static PageResponse from(final Page<?> page) {
        return new PageResponse(
                page.getSize(), page.getTotalPages(), page.getNumber()
        );
    }
}
