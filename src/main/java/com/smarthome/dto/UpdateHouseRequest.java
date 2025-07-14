package com.smarthome.dto;

import lombok.Data;

@Data
public class UpdateHouseRequest {
    private String name;
    private String address;
    private String description;
}
