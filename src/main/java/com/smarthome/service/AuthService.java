package com.smarthome.service;

import com.smarthome.dto.*;
import com.smarthome.model.User;
import com.smarthome.repository.UserRepository;
import com.smarthome.security.JwtTokenProvider;
import com.smarthome.exception.AuthenticationException;
import com.smarthome.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailService emailService;

    public AuthResponse login(String email, String password) {
        User user = validateLogin(email, password);
        String token = jwtTokenProvider.generateToken(user);
        return buildAuthResponse(user, token);
    }


    public UserResponse register(RegisterRequest request) {
        validateEmailUniqueness(request.getEmail());

        User user = buildUserFromRequest(request);
        User savedUser = userRepository.save(user);

        String verificationToken = generateAndStoreVerificationToken(savedUser.getId());
        sendVerificationEmail(savedUser, verificationToken);

        return mapToUserResponse(savedUser);
    }


    public void logout(String token) {
        // Extract token from Bearer header
        String actualToken = token.replace("Bearer ", "");

        // Get user ID from token
        Long userId = jwtTokenProvider.getUserIdFromToken(actualToken);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException("Invalid email");
        }

        // Generate password reset token
        String resetToken = UUID.randomUUID().toString();
//        redisService.storePasswordResetToken(user.getId(), resetToken);

        // Send password reset email
        emailService.sendPasswordResetEmail(user.getEmail(), user.getName(), resetToken);
    }

    public void resetPassword(String token, String newPassword) {
//        Long userId = redisService.getUserIdFromPasswordResetToken(token);

//        if (userId == null) {
//            throw new AuthenticationException("Invalid or expired reset token");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setUpdatedAt(LocalDateTime.now());
//        userRepository.save(user);

//        // Remove reset token from Redis
//        redisService.deletePasswordResetToken(token);
//
//        // Invalidate all refresh tokens for this user
//        redisService.deleteRefreshToken(userId);
    }

    public User getCurrentUser(String token) {
        String actualToken = token.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromToken(actualToken);

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException("Invalid email");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Password not correct");
        }
        return user;
    }
    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthenticationException("Email already exists");
        }
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .success(true)
                .token(token)
                .user(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .role(user.getRole())
                        .permissions(List.of("read", "write", "delete", "admin"))
                        .build())
                .build();
    }

    private User buildUserFromRequest(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .role("user")
                .enabled(false)
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private String generateAndStoreVerificationToken(Long userId) {
        String token = UUID.randomUUID().toString();
//        redisService.storeEmailVerificationToken(userId, token);
        return token;
    }

    private void sendVerificationEmail(User user, String token) {
        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getName(),
                token
        );
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}