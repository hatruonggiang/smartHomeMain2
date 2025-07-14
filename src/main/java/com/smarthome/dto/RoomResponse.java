package com.smarthome.dto;

import lombok.Data;

@Data
public class RoomResponse {
    private Long id;
    private String name;
    private Long houseId;
    private Integer devices;
    private Double temperature;
    private Integer humidity;
    private String createdAt;
}
