package com.smarthome.dto;

import lombok.Data;

import java.util.List;

@Data
public class HouseDetailResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
    private List<RoomResponse> rooms;
    private String createdAt;
    private String updatedAt;
}
