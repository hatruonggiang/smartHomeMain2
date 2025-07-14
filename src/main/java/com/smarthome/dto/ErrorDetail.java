package com.smarthome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {
    private String code;     // ví dụ: "REGISTRATION_FAILED"
    private String message;  // ví dụ: "Email already exists"
}
