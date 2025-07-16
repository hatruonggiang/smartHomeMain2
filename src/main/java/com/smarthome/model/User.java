package com.smarthome.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_users")
@NoArgsConstructor
public class User {

    // ========= CORE INFO =========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 120)
    private String name;

    private String phone;
    private String role;

    // ========= ACCOUNT STATUS =========
    /** Tài khoản bị khoá hay không */
    private boolean enabled = true;

    /** Đã xác minh email hay chưa */
    private boolean emailVerified = false;

    // ========= PASSWORD‑RESET =========
    /** Token đặt lại mật khẩu (null nếu đã dùng hoặc chưa sinh) */
    @Column(name = "reset_token", length = 64)
    private String resetToken;

    /** Hạn sử dụng của resetToken (null nếu resetToken là null) */
    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    // ========= AUDIT =========
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /* --------------------------------------------------------------------- */
    /* ---------------------------  Builder part  --------------------------- */
    /* --------------------------------------------------------------------- */

    private User(Builder builder) {
        this.id               = builder.id;
        this.email            = builder.email;
        this.password         = builder.password;
        this.name             = builder.name;
        this.phone            = builder.phone;
        this.role             = builder.role;
        this.enabled          = builder.enabled;
        this.emailVerified    = builder.emailVerified;
        this.resetToken       = builder.resetToken;
        this.resetTokenExpiry = builder.resetTokenExpiry;
        this.createdAt        = builder.createdAt;
        this.updatedAt        = builder.updatedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String email;
        private String password;
        private String name;
        private String phone;
        private String role;
        private boolean enabled = true;
        private boolean emailVerified = false;
        private String resetToken;
        private LocalDateTime resetTokenExpiry;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        /* --- fluent setters --- */
        public Builder id(Long id){ this.id=id; return this; }
        public Builder email(String email){ this.email=email; return this; }
        public Builder password(String password){ this.password=password; return this; }
        public Builder name(String name){ this.name=name; return this; }
        public Builder phone(String phone){ this.phone=phone; return this; }
        public Builder role(String role){ this.role=role; return this; }
        public Builder enabled(boolean enabled){ this.enabled=enabled; return this; }
        public Builder emailVerified(boolean emailVerified){ this.emailVerified=emailVerified; return this; }
        public Builder resetToken(String resetToken){ this.resetToken=resetToken; return this; }
        public Builder resetTokenExpiry(LocalDateTime resetTokenExpiry){ this.resetTokenExpiry=resetTokenExpiry; return this; }
        public Builder createdAt(LocalDateTime createdAt){ this.createdAt=createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt){ this.updatedAt=updatedAt; return this; }

        public User build(){ return new User(this); }
    }

    /* --------------------------------------------------------------------- */
    /* ---------------------------  Getters/Setters  ------------------------ */
    /* --------------------------------------------------------------------- */

    /* Chỉ liệt kê những setter thực sự cần bên ngoài service,
       các trường khác nên được chỉnh bằng builder hoặc logic riêng */
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public boolean isEnabled() { return enabled; }
    public boolean isEmailVerified() { return emailVerified; }
    public String getResetToken() { return resetToken; }
    public LocalDateTime getResetTokenExpiry() { return resetTokenExpiry; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /* setter ngắn gọn cho hai trường thường‑được‑cập‑nhật */
    public void setPassword(String encoded){ this.password = encoded; }
    public void setUpdatedAt(LocalDateTime updatedAt){ this.updatedAt = updatedAt; }

    /* setter cho password‑reset, dùng trong AuthService */
    public void setResetToken(String token){ this.resetToken = token; }
    public void setResetTokenExpiry(LocalDateTime expiry){ this.resetTokenExpiry = expiry; }
}
