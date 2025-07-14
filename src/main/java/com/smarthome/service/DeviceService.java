//package com.smarthome.service;
//
//import com.smarthome.dto.*;
//import com.smarthome.model.*;
//import com.smarthome.repository.*;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DeviceService {
//    @Autowired
//    private DeviceRepository deviceRepository;
//    @Autowired
//    private RoomRepository roomRepository;
//
//    public Device createDevice(DeviceDTO deviceDTO) {
//        Device device = new Device();
//        device.setName(deviceDTO.getName());
//        device.setType(deviceDTO.getType());
//        device.setDescription(deviceDTO.getDescription());
//        device.setStatus(false);
//        device.setRoom(roomRepository.findById(deviceDTO.getRoomId()).orElseThrow());
//        return deviceRepository.save(device);
//    }
//
//    public Device updateDevice(Long id, DeviceDTO deviceDTO) {
//        Device device = deviceRepository.findById(id).orElseThrow();
//        device.setName(deviceDTO.getName());
//        device.setType(deviceDTO.getType());
//        device.setDescription(deviceDTO.getDescription());
//        return deviceRepository.save(device);
//    }
//
//    public void deleteDevice(Long id) {
//        deviceRepository.deleteById(id);
//    }
//
//    public Device controlDevice(Long id, DeviceControlDTO controlDTO) {
//        Device device = deviceRepository.findById(id).orElseThrow();
//        device.setStatus(controlDTO.isStatus());
//        return deviceRepository.save(device);
//    }
//    public List<Device> getDevices(Long roomId) {
//        return deviceRepository.findByRoomId(roomId);
//    }
//
//    public DeviceResponseDTO toDTO(Device device) {
//        DeviceResponseDTO dto = new DeviceResponseDTO();
//        dto.setId(device.getId());
//        dto.setName(device.getName());
//        dto.setType(device.getType());
//        dto.setStatus(device.isStatus());
//        dto.setDescription(device.getDescription());
//        dto.setRoomId(device.getRoom().getId());
//        return dto;
//    }
//
//}
