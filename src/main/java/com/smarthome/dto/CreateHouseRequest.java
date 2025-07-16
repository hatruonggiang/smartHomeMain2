package com.smarthome.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateHouseRequest {
    private String name;
    private String address;
    private String description;



    //Getter and Setter
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
