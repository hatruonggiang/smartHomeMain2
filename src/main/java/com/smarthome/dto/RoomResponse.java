package com.smarthome.dto;

public class RoomResponse {
    private Long id;
    private String name;

    public RoomResponse(Long id, String name) {
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
}

