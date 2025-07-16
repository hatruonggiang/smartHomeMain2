package com.smarthome.dto;

public class DeviceStateDto {
    private Integer brightness;
    private Double temperature;
    private Integer volume;
    private String color;
    private Integer position;
    private Boolean isLocked;

    //Getter and Setter

    public Integer getBrightness() {
        return brightness;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getVolume() {
        return volume;
    }

    public String getColor() {
        return color;
    }

    public Integer getPosition() {
        return position;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setIsLocked(Boolean islocked) {
        isLocked = islocked;
    }
}

