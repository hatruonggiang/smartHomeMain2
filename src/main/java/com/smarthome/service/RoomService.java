package com.smarthome.service;

import com.smarthome.dto.*;
import com.smarthome.exception.ResourceNotFoundException;
import com.smarthome.model.*;
import com.smarthome.repository.*;
import com.smarthome.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private AuthService authService;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Transactional
    public void updateRoom(String token, Long roomId, AddRoomRequest request) {
        System.out.println("→ Gọi updateRoom với roomId = " + roomId);

        User currentUser = authService.getCurrentUser(token);
        System.out.println("→ Lấy xong currentUser: " + currentUser.getId());

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    System.out.println("→ Không tìm thấy room");
                    return new ResourceNotFoundException("Không tìm thấy phòng với ID: " + roomId);
                });

        House house = room.getHouse();
        System.out.println("→ Room thuộc house: " + house.getId());

        validateOwnership(currentUser, house);
        System.out.println("→ Validate quyền thành công");

        room.setName(request.getName());
        room.setDescription(request.getDescription());
        roomRepository.save(room);
        System.out.println("→ Cập nhật thành công");
    }


    @Transactional
    public void deleteRoom(String token, Long roomId) {
        User currentUser = authService.getCurrentUser(token);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phòng với ID: " + roomId));

        House house = room.getHouse();
        validateOwnership(currentUser, house);

        roomRepository.delete(room);

    }

    @Transactional
    public List<String> getDeviceNamesOfRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng"));

        return room.getDevices().stream()
                .map(Device::getName)
                .toList();
    }
    /* ========= PRIVATE VALIDATORS ========= */


    private void validateOwnership(User user, House house) {
        if (!house.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("Bạn không phải chủ sở hữu của căn nhà này");
        }
    }
}

