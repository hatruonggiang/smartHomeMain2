package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.model.*;
import com.smarthome.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceResponseDTO> createDevice(@RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createDevice(deviceDTO);
        return ResponseEntity.ok(deviceService.toDTO(device)); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevice(@PathVariable Long id, @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.updateDevice(id, deviceDTO);
        return ResponseEntity.ok(deviceService.toDTO(device));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/control")
    public ResponseEntity<DeviceResponseDTO> controlDevice(@PathVariable Long id, @RequestBody DeviceControlDTO controlDTO) {
        Device device = deviceService.controlDevice(id, controlDTO);
        return ResponseEntity.ok(deviceService.toDTO(device));
    }
    @GetMapping
    public ResponseEntity<List<DeviceResponseDTO>> getDevices(@RequestParam Long roomId) {
        List<Device> devices = deviceService.getDevices(roomId);
        List<DeviceResponseDTO> dtos = devices.stream()
            .map(deviceService::toDTO)
            .toList();
        return ResponseEntity.ok(dtos);
    }
    
}
