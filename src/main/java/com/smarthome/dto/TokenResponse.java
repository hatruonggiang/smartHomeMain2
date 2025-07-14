package com.smarthome.dto;

public class TokenResponse {
    private boolean success;
    private String token;
    private String refreshToken;

    // ✅ Constructor private để bắt buộc dùng builder
    private TokenResponse(Builder builder) {
        this.success = builder.success;
        this.token = builder.token;
        this.refreshToken = builder.refreshToken;
    }

    // ✅ Getter
    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    // ✅ Setter
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // ✅ Builder
    public static class Builder {
        private boolean success;
        private String token;
        private String refreshToken;

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public TokenResponse build() {
            return new TokenResponse(this);
        }
    }

    // ✅ Factory method to start builder
    public static Builder builder() {
        return new Builder();
    }
}
