package com.smarthome.dto;
import lombok.Data;

@Data
public class HouseResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String description;
    // KHÔNG đưa members vào nếu không cần
}

