package com.smarthome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success = false;
    private ErrorDetail error;

    // Constructor tiện dụng để truyền code và message trực tiếp
    public ErrorResponse(String code, String message) {
        this.success = false;
        this.error = new ErrorDetail(code, message);
    }
}
