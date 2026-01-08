package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        // 더 직관적이고 표준적인 방식으로 변경
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String getUsername(String token) {
        // verifyWith(secretKey)를 통해 복호화가 아닌 '검증'을 수행
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            // 모든 파싱 에러(만료, 변조, 형식 오류)를 '사용 불가'로 간주
            return true;
        }
    }

    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey) // Jwts.builder가 알고리즘을 자동 추론함
                .compact();
    }
}