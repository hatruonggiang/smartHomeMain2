package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.exception.ResourceNotFoundException;
import com.smarthome.service.DeviceService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDevice(@RequestBody Map<String, Object> request) {
        try {
            deviceService.createDevice(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tạo thiết bị thành công", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Lỗi khi tạo thiết bị"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateDevice(
            @PathVariable("id") Long deviceId,
            @RequestBody Map<String, Object> request) {

        try {
            deviceService.updateDevice(deviceId, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật thiết bị thành công", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Lỗi khi cập nhật thiết bị"));
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ApiResponse<List<DeviceDto>>> getDevicesByRoom(@PathVariable Long roomId) {
        try {
            List<DeviceDto> devices = deviceService.getDevicesByRoomId(roomId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách thiết bị thành công", devices));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Lỗi khi lấy thiết bị"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDevice(@PathVariable("id") Long deviceId) {
        try {
            deviceService.deleteDevice(deviceId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Xóa thiết bị thành công", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Lỗi khi xóa thiết bị"));
        }
    }

    @PostMapping("/{id}/control")
    public ResponseEntity<ApiResponse<Void>> controlDevice(
            @PathVariable("id") Long deviceId,
            @Valid @RequestBody DeviceStateDto request) {

        try {
            deviceService.controlDeviceState(deviceId, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Thiết bị đã được điều khiển thành công", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Lỗi khi điều khiển thiết bị"));
        }
    }
}
