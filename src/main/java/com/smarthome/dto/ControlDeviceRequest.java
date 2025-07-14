package com.smarthome.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ControlDeviceRequest {
    private String action;
    private Map<String, Object> parameters;
}
