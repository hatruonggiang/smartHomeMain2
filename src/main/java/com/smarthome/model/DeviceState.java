package com.smarthome.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "device_states")
@Data
public class DeviceState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private Boolean isOn = false;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer brightness; // 0-100 for lights

    @Column
    private Double temperature; // for thermostat, AC

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer volume; // 0-100 for speakers

    @Column(length = 7)
    private String color; // hex color #ff0000

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer position; // 0-100 for curtains (0=closed, 100=open)

    @Column(nullable = false)
    private Boolean isLocked = false; // for door_lock

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ---------- Getter and Setter ----------


    public Long getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getIsOn() {
        return isOn;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDevice(Device device) {
        this.device = device;
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

    public void setIsLocked(Boolean locked) {
        isLocked = locked;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setIsOn(Boolean on) {
        isOn = on;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getOn() {
        return isOn;
    }

}
