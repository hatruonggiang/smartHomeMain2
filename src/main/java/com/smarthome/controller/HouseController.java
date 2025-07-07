package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.model.*;
import com.smarthome.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<House> createHouse(@RequestBody HouseDTO houseDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(houseService.createHouse(houseDTO, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseResponseDTO> updateHouse(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        House house = houseService.updateHouse(id, houseDTO);
        return ResponseEntity.ok(houseService.toDTO(house));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<HouseResponseDTO>> getHouses() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<House> houses = houseService.getHousesOwnedBy(username);
        List<HouseResponseDTO> dtos = houses.stream()
            .map(houseService::toDTO)
            .toList();
        return ResponseEntity.ok(dtos);
    }
    
    

    @PostMapping("/{id}/members")
    public ResponseEntity<Void> addMember(@PathVariable Long id, @RequestBody Long userId) {
        houseService.addMember(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{houseId}/rooms")
    public ResponseEntity<RoomResponseDTO> addRoomToHouse(
            @PathVariable Long houseId,
            @RequestBody RoomDTO roomDTO) {
        Room room = roomService.createRoom(houseId, roomDTO);
        return ResponseEntity.ok(roomService.toDTO(room));
    }
    
    
}