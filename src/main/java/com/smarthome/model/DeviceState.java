//package com.smarthome.model;
//
//import java.time.LocalDateTime;
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//@Entity
//@Table(name = "device_states")
//@Data
//public class DeviceState {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "device_id", nullable = false)
//    private Device device;
//
//    @Column(columnDefinition = "INT DEFAULT 0")
//    private Integer brightness; // 0-100 for lights
//
//    @Column
//    private Double temperature; // for thermostat, AC
//
//    @Column(columnDefinition = "INT DEFAULT 0")
//    private Integer volume; // 0-100 for speakers
//
//    @Column(length = 7)
//    private String color; // hex color #ff0000
//
//    @Column(columnDefinition = "INT DEFAULT 0")
//    private Integer position; // 0-100 for curtains (0=closed, 100=open)
//
//    @Column(nullable = false)
//    private Boolean isLocked = false; // for door_lock
//
//    @UpdateTimestamp
//    private LocalDateTime lastUpdate;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//}
