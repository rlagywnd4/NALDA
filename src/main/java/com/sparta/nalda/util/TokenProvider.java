package com.sparta.nalda.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final SecretKey key;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간

    public TokenProvider(@Value("${secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Claims claims = Jwts.claims()
            .subject(userDetails.getUsername())
            .build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(validity)
            .claim("userRole", userDetails.getAuthorities().toString())
            .signWith(key)
            .compact();
    }

}
