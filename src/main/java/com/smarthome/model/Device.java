package com.smarthome.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.smarthome.enums.DeviceType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_devices")
public class Device {

    // ---------- Basic info ----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType type;

    // ---------- Relationship ----------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private DeviceState deviceState;

    // ---------- Extra metadata ----------
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode properties;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode capabilities;


    // ---------- Timestamps ----------
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ---------- Getter and Setter ----------

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeviceType getType() {
        return type;
    }

    public Room getRoom() {
        return room;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public JsonNode getProperties() {
        return properties;
    }

    public JsonNode getCapabilities() {
        return capabilities;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
        if (deviceState != null) {
            deviceState.setDevice(this);
        }
    }

    public void setProperties(JsonNode properties) {
        this.properties = properties;
    }

    public void setCapabilities(JsonNode capabilities) {
        this.capabilities = capabilities;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
