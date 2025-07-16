package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.exception.AuthenticationException;
import com.smarthome.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(HouseController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse<>(true, "Đăng nhập thành công", token));
        } catch (AuthenticationException e) {
            // lỗi xác thực
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi đăng nhập", e);
            // lỗi hệ thống
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống",null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Đăng ký thành công", null));
        } catch (AuthenticationException e) {
            // lỗi xác thực
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi đăng ký", e);
           // lỗi hệ thống
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            authService.forgotPassword(request.getEmail());
            return ResponseEntity
                    .ok(new ApiResponse<>(true, "Email khôi phục đã được gửi", null));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi gửi email khôi phục", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity
                    .ok(new ApiResponse<>(true, "Đặt lại mật khẩu thành công", null));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi đặt lại mật khẩu", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }

}