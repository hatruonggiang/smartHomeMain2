package com.smarthome.dto;

public class AuthResponse {
    private boolean success;
    private String token;
//    private String refreshToken;
    private UserResponse user;
    private String role;

    // Constructor private: bắt buộc dùng builder
    private AuthResponse(Builder builder) {
        this.success = builder.success;
        this.token = builder.token;
//        this.refreshToken = builder.refreshToken;
        this.user = builder.user;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

//    public String getRefreshToken() {
//        return refreshToken;
//    }

    public UserResponse getUser() {
        return user;
    }

    // Builder nội bộ
    public static class Builder {
        private boolean success;
        private String token;
//        private String refreshToken;
        private UserResponse user;

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

//        public Builder refreshToken(String refreshToken) {
//            this.refreshToken = refreshToken;
//            return this;
//        }

        public Builder user(UserResponse user) {
            this.user = user;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }

    // Factory method
    public static Builder builder() {
        return new Builder();
    }
}
