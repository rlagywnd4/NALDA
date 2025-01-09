package com.sparta.nalda.util;

import com.sparta.nalda.dto.user.NaldaUserDetails;
import com.sparta.nalda.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    private final SecretKey key;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간

    public TokenProvider(@Value("${secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(Authentication auth) {
        NaldaUserDetails userDetails = (NaldaUserDetails) auth.getPrincipal(); // Custom UserDetails
        UserEntity userEntity = userDetails.getUserEntity();

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
            .claim("id", String.valueOf(userEntity.getId()))
            .claim("email", userEntity.getEmail())
            .claim("userRole", "ROLE_" + userEntity.getUserRole())
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact();
    }

    public String getId(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("id", String.class);
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getUserRole(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("userRole", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

}
