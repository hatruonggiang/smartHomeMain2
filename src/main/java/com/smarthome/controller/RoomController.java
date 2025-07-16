package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.exception.ResourceNotFoundException;
import com.smarthome.model.*;
import com.smarthome.service.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    private DeviceService deviceService;


    @PutMapping("/{id}")                // id = roomId
    public ResponseEntity<ApiResponse> updateRoom(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @Valid @RequestBody AddRoomRequest request) {

        try {
            String token = extractToken(authHeader);
            roomService.updateRoom(token, id, request);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true,"Đã cập nhật phòng thành công",null));

        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, ex.getMessage()));

        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage()));

        } catch (IllegalArgumentException ex) {          // vi phạm validation đầu vào
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, ex.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "Lỗi máy chủ khi sửa phòng"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRoom(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {

        try {
            String token = authHeader.replace("Bearer ", "");
            roomService.deleteRoom(token, id);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Xóa phòng thành công",null));

        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, e.getMessage()));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ApiResponse(false, "Lỗi máy chủ khi delete room"));
        }
    }
    @GetMapping("/devices")
    public ResponseEntity<List<String>> getDeviceNamesOfRoom(@PathVariable Long roomId) {
        List<String> deviceNames = roomService.getDeviceNamesOfRoom(roomId);
        return ResponseEntity.ok(deviceNames);
    }
    private String extractToken(String header) {
        if (header == null) {
            throw new RuntimeException("Thiếu Authorization header");
        }

        String trimmed = header.trim();
        if (trimmed.toLowerCase().startsWith("bearer ")) {
            return trimmed.substring(7).trim();
        }

        throw new RuntimeException("Authorization header không hợp lệ (phải bắt đầu bằng Bearer )");
    }




}


