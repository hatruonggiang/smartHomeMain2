package com.smarthome.dto;

import lombok.Data;

@Data
public class DeviceResponse {
    private Long id;
    private String name;
    private String type;
    private String status;
    private String state;
    private Integer brightness;
    private Long roomId;
    private String lastUpdate;
}
