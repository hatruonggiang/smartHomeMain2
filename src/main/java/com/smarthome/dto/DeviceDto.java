package com.smarthome.dto;

import java.util.Map;

public class DeviceDto {
    private Long id;
    private String name;
    private String type;
    private String roomName;
    private Boolean isOn;
    private Map<String, Object> state; // Trường động tùy theo loại

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

    public Boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(Boolean on) {
        isOn = on;
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
    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }
}
