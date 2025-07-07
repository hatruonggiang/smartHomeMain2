package com.smarthome.dto;

import lombok.Data;

@Data
public class DeviceDTO {
    private String name;
    private String type;
    private Long roomId;
    private String description;
}
