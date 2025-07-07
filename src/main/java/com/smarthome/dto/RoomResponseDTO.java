package com.smarthome.dto;

import lombok.Data;

@Data
public class RoomResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long houseId; // optional: nếu muốn biết room thuộc house nào
}
