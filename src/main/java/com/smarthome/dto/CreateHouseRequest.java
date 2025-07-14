package com.smarthome.dto;

import lombok.Data;

@Data
public class CreateHouseRequest {
    private String name;
    private String address;
    private String description;
}
