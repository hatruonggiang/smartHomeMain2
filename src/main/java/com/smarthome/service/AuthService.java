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
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailService emailService;


    public String login(String email, String password) {
        User user = validateLogin(email, password);
        String token = jwtTokenProvider.generateToken(user);
        return token;
    }

    public void register(RegisterRequest request) {
        validateRegisterRequest(request);

        User user = buildUserFromRequest(request);
        userRepository.save(user);
    }
    /**
     * Gửi email chứa token đặt lại mật khẩu cho người dùng.
     */
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException("Email không tồn tại trong hệ thống");
        }

        // Sinh mã OTP 6 chữ số
        String otp = String.format("%06d", new Random().nextInt(1000000));

        // Thời hạn OTP là 10 phút
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

        // Lưu vào user (hoặc bảng riêng nếu bạn tách OTP riêng)
        user.setResetToken(otp);
        user.setResetTokenExpiry(expiryTime);
        userRepository.save(user);

        // Gửi email chứa mã OTP
        emailService.sendOtpEmail(
                user.getEmail(),
                user.getName(),
                otp
        );
    }

    /**
     * Đặt lại mật khẩu dựa vào token khôi phục.
     */
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user == null) {
            throw new AuthenticationException("Token không hợp lệ");
        }

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new AuthenticationException("Token đã hết hạn");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new AuthenticationException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }

        // Mã hoá và cập nhật mật khẩu
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Vô hiệu hoá token
        user.setResetTokenExpiry(null);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }




    private User buildUserFromRequest(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getUserName())
                .phone(request.getPhone())
                .role("user")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    private void sendVerificationEmail(User user, String token) {
        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getName(),
                token
        );
    }


    //Validation
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

    private void validateRegisterRequest(RegisterRequest request) {
        String email = request.getEmail();
        String userName = request.getUserName();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();

        if (email == null || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new AuthenticationException("Email không hợp lệ");
        }
        if (userRepository.existsByEmail(email)) {
            throw new AuthenticationException("Email đã tồn tại");
        }
        if (userName == null || userName.trim().isEmpty()) {
            throw new AuthenticationException("Tên người dùng không được để trống");
        }
        if (password == null || password.length() < 6) {
            throw new AuthenticationException("Password phải có ít nhất 6 ký tự");
        }
        if (!password.equals(confirmPassword)) {
            throw new AuthenticationException("Password không khớp");
        }
    }
    public User getCurrentUser(String token) {
        // 1. Bỏ "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 2. Trích xuất userId từ token
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        if (userId == null) {
            throw new AuthenticationException("Token không hợp lệ hoặc hết hạn");
        }

        // 3. Truy vấn DB
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + userId);
        }

        return optionalUser.get();
    }
    public UserProfileResponse getUserProfile(String token) {
        // 1. Loại bỏ "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 2. Lấy user từ token
        User user = getCurrentUser(token); // đã kiểm tra null + expired

        // 3. Trả về DTO
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }





}