package com.smarthome.dto;

import lombok.Data;

@Data
public class CreateRoomRequest {
    private String name;
    private Long houseId;
    private String description;
}
