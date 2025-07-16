package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.model.*;
import com.smarthome.service.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<?> createDevice(@RequestBody Map<String, Object> request) {
        deviceService.createDevice(request);
        return ResponseEntity.ok("Tạo thiết bị thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevice(
            @PathVariable("id") Long deviceId,
            @RequestBody Map<String, Object> request) {
        deviceService.updateDevice(deviceId, request);
        return ResponseEntity.ok("Cập nhật thiết bị thành công");
    }
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<DeviceDto>> getDevicesByRoom(@PathVariable Long roomId) {
        List<DeviceDto> devices = deviceService.getDevicesByRoomId(roomId);
        return ResponseEntity.ok(devices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Long deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.ok("Xóa thiết bị thành công");
    }
    @PostMapping("/{id}/control")
    public ResponseEntity<?> controlDevice(
            @PathVariable("id") Long deviceId,
            @RequestBody DeviceStateDto request) {
        deviceService.controlDeviceState(deviceId, request);
        return ResponseEntity.ok("Thiết bị đã được điều khiển thành công");
    }




}
