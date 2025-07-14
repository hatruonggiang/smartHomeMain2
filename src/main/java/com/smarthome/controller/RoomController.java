//package com.smarthome.controller;
//
//import com.smarthome.dto.*;
//import com.smarthome.model.*;
//import com.smarthome.service.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/rooms")
//public class RoomController {
//    @Autowired
//    private RoomService roomService;
//
//    @PutMapping("/{id}")
//    public ResponseEntity<RoomResponseDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
//        Room room = roomService.updateRoom(id, roomDTO);
//        return ResponseEntity.ok(roomService.toDTO(room));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
//        roomService.deleteRoom(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<RoomResponseDTO>> getRooms(@RequestParam Long houseId) {
//        List<Room> rooms = roomService.getRooms(houseId);
//        List<RoomResponseDTO> dtos = rooms.stream()
//            .map(roomService::toDTO)
//            .toList();
//        return ResponseEntity.ok(dtos);
//    }
//}
