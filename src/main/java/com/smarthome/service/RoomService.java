//package com.smarthome.service;
//
//import com.smarthome.dto.*;
//import com.smarthome.model.*;
//import com.smarthome.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class RoomService {
//    @Autowired
//    private RoomRepository roomRepository;
//    @Autowired
//    private HouseRepository houseRepository;
//
//    public Room createRoom(Long houseId, RoomDTO dto) {
//        House house = houseRepository.findById(houseId).orElseThrow();
//        Room room = new Room();
//        room.setName(dto.getName());
//        room.setDescription(dto.getDescription());
//        room.setHouse(house);
//        return roomRepository.save(room);
//    }
//
//    public Room updateRoom(Long id, RoomDTO roomDTO) {
//        Room room = roomRepository.findById(id).orElseThrow();
//        room.setName(roomDTO.getName());
//        room.setDescription(roomDTO.getDescription());
//        return roomRepository.save(room);
//    }
//
//    public void deleteRoom(Long id) {
//        roomRepository.deleteById(id);
//    }
//
//    public List<Room> getRooms(Long houseId) {
//        House house = houseRepository.findById(houseId).orElseThrow();
//        return house.getRooms();
//    }
//
//
//    public RoomResponseDTO toDTO(Room room) {
//        RoomResponseDTO dto = new RoomResponseDTO();
//        dto.setId(room.getId());
//        dto.setName(room.getName());
//        dto.setDescription(room.getDescription());
//        dto.setHouseId(room.getHouse().getId());
//        return dto;
//    }
//}
