package com.smarthome.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddRoomRequest {

    private String name;

    private String description;

    //Getter and Setter


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName( String name) {
        this.name = name;
    }

    public void setDescription( String desciption) {
        this.description = desciption;
    }
}
