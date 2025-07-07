package com.smarthome.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private boolean status;
    private String description;
    @ManyToOne
    private Room room;
}