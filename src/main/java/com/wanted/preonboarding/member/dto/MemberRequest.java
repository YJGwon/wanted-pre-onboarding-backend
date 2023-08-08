package com.wanted.preonboarding.member.dto;

import com.wanted.preonboarding.member.domain.Email;
import com.wanted.preonboarding.member.domain.Password;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record MemberRequest(
        @Pattern(regexp = Email.REGEX, message = Email.INVALID_FORMAT_MESSAGE) String email,
        @Length(min = Password.MIN_LENGTH, message = Password.TOO_SHORT_MESSAGE) String password
) {
}
