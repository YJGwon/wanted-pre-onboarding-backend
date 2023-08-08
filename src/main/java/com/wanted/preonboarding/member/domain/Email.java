package com.wanted.preonboarding.member.domain;

import static lombok.AccessLevel.PROTECTED;

import com.wanted.preonboarding.common.exception.InvalidInputException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Email {

    public static final String REGEX = "^.*@.*$";
    public static final String INVALID_FORMAT_MESSAGE = "이메일은 @를 포함해야 합니다.";

    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Column(name = "email")
    private String value;

    public Email(final String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(final String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new InvalidInputException(INVALID_FORMAT_MESSAGE);
        }
    }
}
