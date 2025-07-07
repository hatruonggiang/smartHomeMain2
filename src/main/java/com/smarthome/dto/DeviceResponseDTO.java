package com.smarthome.dto;

import lombok.Data;

@Data
public class DeviceResponseDTO {
    private Long id;
    private String name;
    private String type;
    private boolean status;
    private String description; // ✅ thêm trường mô tả
    private Long roomId;        // ✅ để biết thiết bị thuộc phòng nào
}
