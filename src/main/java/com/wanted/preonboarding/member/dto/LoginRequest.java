package com.wanted.preonboarding.member.dto;

import com.wanted.preonboarding.member.domain.Email;
import com.wanted.preonboarding.member.domain.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Schema(description = "이메일", example = "example@wanted.co.kr")
        @Pattern(regexp = Email.REGEX, message = Email.INVALID_FORMAT_MESSAGE)
        String email,
        @Schema(description = "비밀번호", example = "password")
        @Length(min = Password.MIN_LENGTH, message = Password.TOO_SHORT_MESSAGE)
        String password
) {
}
