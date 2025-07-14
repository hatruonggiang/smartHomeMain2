package com.smarthome.dto;

import lombok.Data;

import java.util.Map;
public class UpdateDeviceRequest {
    private String name;
    private Map<String, Object> properties;
}
