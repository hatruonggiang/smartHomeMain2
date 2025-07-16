package com.smarthome.dto;

import com.smarthome.enums.HouseMemberRole;
import jakarta.validation.constraints.NotBlank;

public class AddMemberRequest {
    private Long houseId;
    private Long userId;
    private HouseMemberRole role; // enum: MEMBER, ADMIN,

    //Getter and Setter

    public Long getHouseId() {
        return houseId;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseMemberRole getRole() {
        return role;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRole(HouseMemberRole role) {
        this.role = role;
    }

    //Contractor

    public AddMemberRequest(Long houseId, Long userId, HouseMemberRole role) {
        this.houseId = houseId;
        this.userId = userId;
        this.role = role;
    }
}

