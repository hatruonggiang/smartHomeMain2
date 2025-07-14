package com.smarthome.dto;

import java.util.List;

public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String role;
    private List<String> permissions;

    // Constructor private để bắt buộc dùng builder
    private UserResponse(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.name = builder.name;
        this.role = builder.role;
        this.permissions = builder.permissions;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    // Factory method
    public static Builder builder() {
        return new Builder();
    }

    // Builder nội bộ
    public static class Builder {
        private Long id;
        private String email;
        private String name;
        private String role;
        private List<String> permissions;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder permissions(List<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
}
