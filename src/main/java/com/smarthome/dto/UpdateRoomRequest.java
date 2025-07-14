package com.smarthome.dto;

import lombok.Data;

@Data
public class UpdateRoomRequest {
    private String name;
    private String description;
}
