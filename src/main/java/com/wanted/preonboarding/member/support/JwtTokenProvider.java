package com.wanted.preonboarding.member.support;

import com.wanted.preonboarding.member.domain.TokenProvider;
import com.wanted.preonboarding.member.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret-key}") final String secretKey,
                            @Value("${jwt.expire-length}") final long validityInMilliseconds) {
        final String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    @Override
    public String create(final String subject) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void validate(final String token) {
        try {
            parseClaimsJws(token);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    public String extractSubject(final String token) {
        return parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Jws<Claims> parseClaimsJws(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
