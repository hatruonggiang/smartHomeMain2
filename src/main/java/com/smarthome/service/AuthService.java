package com.smarthome.service;

import com.smarthome.dto.*;
import com.smarthome.model.*;
import com.smarthome.repository.*;
import com.smarthome.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    EmailService emailService;

    public String register(RegisterRequest request) {
        System.out.println("Username from request: " + request.getUsername());
        System.out.println("Password from request: " + request.getPassword());
        System.out.println("Email from request: " + request.getEmail());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        System.out.println("User before save - Username: " + user.getUsername());

        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        System.out.println("Email from request: " + email);
        System.out.println("Password from request: " + password);

        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail());
        }
        throw new RuntimeException("Invalid credentials");
    }

    public String forgotPassword(ForgotPasswordRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return "User not found with email: " + request.getEmail();
        }

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        emailService.sendResetPasswordEmail(user.getEmail(), token);

        return "Password reset link has been sent";
    }

    public String resetPassword(ResetPasswordRequestDTO request) {
        String newPassword = request.getNewPassword();
        String token = request.getToken();
        String confirmPassword = request.getConfirmPassword();
        User user = userRepository.findByResetPasswordToken(token);

        if(user == null){
            return "Reset token has expired";
        }
        if(newPassword == null || newPassword.trim().length() < 6){
            return "Password must be at least 6 characters long";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "Passwords do not match";
        }
        if (user.getResetTokenExpiry() != null &&
                user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return "Reset token has expired";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        return "Password has been reset successfully";
    }
    public UserInfoDTO getCurrentUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserInfoDTO(user.getId(), user.getUsername(), user.getEmail());
    }

}