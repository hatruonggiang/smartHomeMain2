package com.smarthome.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}") // tính bằng giây
    private long jwtExpiration;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000L))
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .claim("roles", List.of("ROLE_USER"))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token) {
        return parse(token).get("email", String.class);
    }

    public List<String> getRoles(String token) {
        Object raw = parse(token).get("roles");
        return objectMapper.convertValue(raw, new TypeReference<>() {});
    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(parse(token).getSubject());
    }
}
