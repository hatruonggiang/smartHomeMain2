//package com.smarthome.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class RedisService {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
//    private static final String EMAIL_VERIFICATION_PREFIX = "email_verification:";
//    private static final String PASSWORD_RESET_PREFIX = "password_reset:";
//    private static final String BLACKLIST_TOKEN_PREFIX = "blacklist_token:";
//
//    // Token expiration times (in seconds)
//    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60; // 7 days
//    private static final long EMAIL_VERIFICATION_EXPIRATION = 24 * 60 * 60; // 24 hours
//    private static final long PASSWORD_RESET_EXPIRATION = 60 * 60; // 1 hour
//    private static final long BLACKLIST_TOKEN_EXPIRATION = 24 * 60 * 60; // 24 hours
//
//    // Refresh Token operations
//    public void storeRefreshToken(Long userId, String refreshToken) {
//        String key = REFRESH_TOKEN_PREFIX + userId;
//        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.SECONDS);
//    }
//
//    public boolean validateRefreshToken(Long userId, String refreshToken) {
//        String key = REFRESH_TOKEN_PREFIX + userId;
//        String storedToken = (String) redisTemplate.opsForValue().get(key);
//        return storedToken != null && storedToken.equals(refreshToken);
//    }
//
//    public void deleteRefreshToken(Long userId) {
//        String key = REFRESH_TOKEN_PREFIX + userId;
//        redisTemplate.delete(key);
//    }
//
//    // Email verification token operations
//    public void storeEmailVerificationToken(Long userId, String token) {
//        String userKey = EMAIL_VERIFICATION_PREFIX + "user:" + userId;
//        String tokenKey = EMAIL_VERIFICATION_PREFIX + "token:" + token;
//
//        redisTemplate.opsForValue().set(userKey, token, EMAIL_VERIFICATION_EXPIRATION, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(tokenKey, userId, EMAIL_VERIFICATION_EXPIRATION, TimeUnit.SECONDS);
//    }
//
//    public Long getUserIdFromEmailVerificationToken(String token) {
//        String key = EMAIL_VERIFICATION_PREFIX + "token:" + token;
//        Object userId = redisTemplate.opsForValue().get(key);
//        return userId != null ? Long.parseLong(userId.toString()) : null;
//    }
//
//    public void deleteEmailVerificationToken(String token) {
//        Long userId = getUserIdFromEmailVerificationToken(token);
//        if (userId != null) {
//            String userKey = EMAIL_VERIFICATION_PREFIX + "user:" + userId;
//            String tokenKey = EMAIL_VERIFICATION_PREFIX + "token:" + token;
//            redisTemplate.delete(userKey);
//            redisTemplate.delete(tokenKey);
//        }
//    }
//
//    // Password reset token operations
//    public void storePasswordResetToken(Long userId, String token) {
//        String userKey = PASSWORD_RESET_PREFIX + "user:" + userId;
//        String tokenKey = PASSWORD_RESET_PREFIX + "token:" + token;
//
//        redisTemplate.opsForValue().set(userKey, token, PASSWORD_RESET_EXPIRATION, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(tokenKey, userId, PASSWORD_RESET_EXPIRATION, TimeUnit.SECONDS);
//    }
//
//    public Long getUserIdFromPasswordResetToken(String token) {
//        String key = PASSWORD_RESET_PREFIX + "token:" + token;
//        Object userId = redisTemplate.opsForValue().get(key);
//        return userId != null ? Long.parseLong(userId.toString()) : null;
//    }
//
//    public void deletePasswordResetToken(String token) {
//        Long userId = getUserIdFromPasswordResetToken(token);
//        if (userId != null) {
//            String userKey = PASSWORD_RESET_PREFIX + "user:" + userId;
//            String tokenKey = PASSWORD_RESET_PREFIX + "token:" + token;
//            redisTemplate.delete(userKey);
//            redisTemplate.delete(tokenKey);
//        }
//    }
//
//    // Token blacklist operations
//    public void blacklistToken(String token) {
//        String key = BLACKLIST_TOKEN_PREFIX + token;
//        redisTemplate.opsForValue().set(key, "blacklisted", BLACKLIST_TOKEN_EXPIRATION, TimeUnit.SECONDS);
//    }
//
//    public boolean isTokenBlacklisted(String token) {
//        String key = BLACKLIST_TOKEN_PREFIX + token;
//        return redisTemplate.hasKey(key);
//    }
//
//    // Generic operations
//    public void store(String key, Object value, long expiration, TimeUnit timeUnit) {
//        redisTemplate.opsForValue().set(key, value, expiration, timeUnit);
//    }
//
//    public Object get(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//    public boolean hasKey(String key) {
//        return redisTemplate.hasKey(key);
//    }
//
//    public void setExpiration(String key, long expiration, TimeUnit timeUnit) {
//        redisTemplate.expire(key, expiration, timeUnit);
//    }
//}