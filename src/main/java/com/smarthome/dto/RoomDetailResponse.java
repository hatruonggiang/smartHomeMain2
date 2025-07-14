package com.smarthome.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomDetailResponse {
    private Long id;
    private String name;
    private Long houseId;
    private String description;
    private List<DeviceResponse> devices;
    private Double temperature;
    private Integer humidity;
    private String createdAt;
}
