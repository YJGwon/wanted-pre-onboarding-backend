package com.wanted.preonboarding.member.support;

import com.wanted.preonboarding.member.domain.TokenProvider;
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
}
