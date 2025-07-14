package com.smarthome.dto;

import lombok.Data;

@Data
public class HouseResponse {
    private Long id;
    private String name;
    private String address;
    private Integer rooms;
    private Integer devices;
    private String createdAt;
    private String updatedAt;
}
