package com.smarthome.dto;

public class DeviceDto {
    private Long id;
    private String name;
    private String type;
    private String roomName;

    //Getter and Setter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
