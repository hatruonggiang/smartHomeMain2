package com.smarthome.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateSettingsRequest {
    private String theme;
    private Map<String, Object> notifications;
    private Map<String, Object> privacy;
}
