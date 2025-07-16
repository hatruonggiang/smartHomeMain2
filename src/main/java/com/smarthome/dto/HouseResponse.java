package com.smarthome.dto;

import com.smarthome.enums.HouseMemberRole;

public class HouseResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
    private HouseMemberRole role; // OWNER, ADMIN, VIEWER...

    public HouseResponse(Long id, String name, String address, String description, HouseMemberRole role) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.role = role;
    }

    public HouseResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    //Getter and Setter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public HouseMemberRole getRole() {
        return role;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRole(HouseMemberRole role) {
        this.role = role;
    }
}
