package com.wanted.preonboarding.post.dto;

import org.springframework.data.domain.Page;

public record PageResponse(
        int size,
        int totalPages,
        int currentPage,
        boolean hasNext
) {

    public static PageResponse from(final Page<?> page) {
        return new PageResponse(
                page.getSize(), page.getTotalPages(), page.getNumber(), page.hasNext()
        );
    }
}
