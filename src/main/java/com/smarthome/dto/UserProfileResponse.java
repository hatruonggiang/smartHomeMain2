package com.smarthome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfileResponse {
    private Long id;
    private String userName;
    private String phone;
    private String email;
    private LocalDateTime createdAt;

    public UserProfileResponse(Long id, String userName, String phone, String email, LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
    }

}
