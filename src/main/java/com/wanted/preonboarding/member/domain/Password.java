package com.wanted.preonboarding.member.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public final class Password {

    @Column(name = "password")
    private String value;

    public static Password fromPlainText(final String plainText) {
        return new Password(encrypt(plainText));
    }

    public boolean isSamePassword(final String plainText) {
        return value.equals(encrypt(plainText));
    }

    private static String encrypt(final String plainText) {
        final MessageDigest messageDigest = getMessageDigestInstance();
        final byte[] hash = messageDigest.digest(plainText.getBytes(StandardCharsets.UTF_8));

        return bytesToHexString(hash);
    }

    private static String bytesToHexString(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private static MessageDigest getMessageDigestInstance() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
