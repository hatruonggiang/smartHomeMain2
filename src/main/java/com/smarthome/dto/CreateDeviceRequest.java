package com.smarthome.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CreateDeviceRequest {
    private String name;
    private String type;
    private Long roomId;
    private Map<String, Object> properties;
}
