package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.domain.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthProperties properties;

    private String buildToken(Map<String, Object> claims, String subject) {
        var now = System.currentTimeMillis();
        var expiration = now + properties.getToken().getExpiration();

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuer("spring-email")
                .issuedAt(new Date(now))
                .expiration(new Date(expiration))
                .signWith(secretKey())
                .compact();
    }


    public String generateToken(User user, Map<String, Object> claims) {
        return buildToken(claims, user.getId());
    }

    public boolean isTokenValid(String token, String subject) {
        try {
            final String userId = getTokenSubject(token);
            return userId.equals(subject) && getTokenExpiration(token).after(new Date());
        } catch (SignatureException ex) {
            return false;
        }
    }

    public String getTokenSubject(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    private Date getTokenExpiration(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(properties.getToken().getSecret().getBytes(StandardCharsets.UTF_8));
    };
}
