package com.smarthome.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    
    @ManyToOne
    private House house;

    @OneToMany(mappedBy = "room")
    private List<Device> devices;
    
}