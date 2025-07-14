package com.smarthome.model;

import com.smarthome.enums.DeviceStatus;
import com.smarthome.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_devices")
@Data
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

    // ---------- Online / offline ----------
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status = DeviceStatus.OFFLINE;

    // ---------- Simple ON / OFF / STANDBY enum ----------
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.smarthome.enums.DeviceState state = com.smarthome.enums.DeviceState.OFF;

    // ---------- Room relationship ----------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // ---------- Extra metadata ----------
    @Column(columnDefinition = "JSON")
    private String properties;      // e.g. {"manufacturer":"Philips","model":"Hue White"}

    @Column(columnDefinition = "JSON")
    private String capabilities;    // e.g. ["turn_on","turn_off","set_brightness"]

    // ---------- Timestamps ----------
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    // ---------- Detailed runtime state ----------
//    @OneToOne(mappedBy = "device",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            orphanRemoval = true)
//    private DeviceState deviceState;   // entity com.smarthome.model.DeviceState
}
