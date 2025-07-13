package com.smarthome.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private static final String SECRET = "hatruonggianghatruonggianghatruonggianghatruonggianghatruonggianghatruonggianghatruonggianghatruonggianghatruonggiang";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in ms

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email) {
        return Jwts.builder()
                .claim("roles", java.util.List.of("ROLE_USER"))
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Ghi log nếu cần: token sai, hết hạn, v.v.
            return false;
        }
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object rawRoles = claims.get("roles");

        try {
            return objectMapper.convertValue(rawRoles, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return List.of(); // Trả về rỗng nếu ép kiểu thất bại
        }
    }



}
