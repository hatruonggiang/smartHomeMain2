package com.smarthome.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.dto.DeviceDto;
import com.smarthome.enums.*;
import com.smarthome.exception.ResourceNotFoundException;
import com.smarthome.model.*;
import com.smarthome.repository.*;
import com.smarthome.dto.DeviceStateDto;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DeviceStateRepository deviceStateRepository;

    @Transactional
    public void createDevice(Map<String, Object> request) {
        String name = (String) request.get("name");
        String typeStr = (String) request.get("type");
        Long roomId = ((Number) request.get("roomId")).longValue();

        DeviceType type = DeviceType.valueOf(typeStr);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng"));

        Device device = new Device();
        device.setName(name);
        device.setType(type);
        device.setRoom(room);

        // set properties + capabilities
        ObjectMapper mapper = new ObjectMapper();
            if (request.containsKey("properties")) {
                device.setProperties(mapper.convertValue(request.get("properties"), JsonNode.class));
            }
            if (request.containsKey("capabilities")) {
                device.setCapabilities(mapper.convertValue(request.get("capabilities"), JsonNode.class));
            }
        if (request.containsKey("state")) {
            DeviceStateDto dto = mapper.convertValue(request.get("state"), DeviceStateDto.class);
            DeviceState state = buildDeviceState(device, type, dto);
            device.setDeviceState(state);
        }
        deviceRepository.save(device);
    }
    @Transactional
    public void updateDevice(Long deviceId, Map<String, Object> request) {
        // 1. Tìm thiết bị cần cập nhật
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thiết bị với ID: " + deviceId));

        ObjectMapper mapper = new ObjectMapper();

        /* ---------- Cập nhật các trường cơ bản ---------- */
        if (request.containsKey("name")) {
            device.setName((String) request.get("name"));
        }

        if (request.containsKey("roomId")) {
            Long roomId = ((Number) request.get("roomId")).longValue();
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng"));
            device.setRoom(room);
        }

        /* ---------- Cập nhật loại thiết bị (nếu được phép) ---------- */
        if (request.containsKey("type")) {
            DeviceType newType = DeviceType.valueOf((String) request.get("type"));
            device.setType(newType);
        }
        DeviceType effectiveType = device.getType();   // dùng cho phần state

        /* ---------- Cập nhật properties & capabilities ---------- */
        if (request.containsKey("properties")) {
            device.setProperties(mapper.convertValue(request.get("properties"), JsonNode.class));
        }
        if (request.containsKey("capabilities")) {
            device.setCapabilities(mapper.convertValue(request.get("capabilities"), JsonNode.class));
        }

        /* ---------- Cập nhật state ---------- */
        if (request.containsKey("state")) {
            DeviceStateDto dto = mapper.convertValue(request.get("state"), DeviceStateDto.class);

            DeviceState state = device.getDeviceState();
            if (state == null) {
                // Chưa có state → tạo mới
                state = buildDeviceState(device, effectiveType, dto);
                device.setDeviceState(state);
            } else {
                // Đã có state → chỉ sửa các trường liên quan
                switch (effectiveType) {
                    case LIGHT -> {
                        if (dto.getBrightness() != null) state.setBrightness(dto.getBrightness());
                        if (dto.getColor() != null)       state.setColor(dto.getColor());
                    }
                    case SPEAKER -> {
                        if (dto.getVolume() != null)      state.setVolume(dto.getVolume());
                    }
                    case DOOR_LOCK -> {
                        if (dto.getIsLocked() != null)    state.setIsLocked(Boolean.TRUE.equals(dto.getIsLocked()));
                    }
                    case CURTAIN -> {
                        if (dto.getPosition() != null)    state.setPosition(dto.getPosition());
                    }
                }
            }
        }

        /* ---------- Lưu thay đổi ---------- */
        deviceRepository.save(device);
    }
    public List<DeviceDto> getDevicesByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng với ID: " + roomId));

        return room.getDevices().stream().map(device -> {
            DeviceDto dto = new DeviceDto();
            dto.setId(device.getId());
            dto.setName(device.getName());
            dto.setType(device.getType().name());
            dto.setRoomName(room.getName());
            dto.setIsOn(device.getIsOn());

            DeviceState state = deviceStateRepository.findByDevice(device)
                    .orElse(null); // hoặc throw nếu cần

            Map<String, Object> stateMap = new HashMap<>();
            if (state != null) {
                stateMap.put("isOn", state.getIsOn());

                switch (device.getType()) {
                    case LIGHT -> {
                        stateMap.put("brightness", state.getBrightness());
                        stateMap.put("color", state.getColor());
                    }
                    case SPEAKER -> {
                        stateMap.put("volume", state.getVolume());
                    }
                    case DOOR_LOCK -> {
                        stateMap.put("isLocked", state.getIsLocked());
                    }
                    case CURTAIN -> {
                        stateMap.put("position", state.getPosition());
                    }
                }
            }

            dto.setState(stateMap);
            return dto;
        }).toList();
    }
    @Transactional
    public void deleteDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thiết bị với ID: " + deviceId));
        deviceRepository.delete(device);
    }
    @Transactional
    public void controlDeviceState(Long deviceId, DeviceStateDto dto) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thiết bị với ID: " + deviceId));

        DeviceType type = device.getType();
        DeviceState state = device.getDeviceState();
        if (dto.getIsOn() != null) {
            state.setIsOn(dto.getIsOn());
            device.setisOn(dto.getIsOn());
        }

        if (state == null) {
            state = buildDeviceState(device, type, dto);
            device.setDeviceState(state);
        } else {
            switch (type) {
                case LIGHT -> {
                    if (dto.getBrightness() != null) state.setBrightness(dto.getBrightness());
                    if (dto.getColor() != null)       state.setColor(dto.getColor());
                }
                case SPEAKER -> {
                    if (dto.getVolume() != null)      state.setVolume(dto.getVolume());
                }
                case DOOR_LOCK -> {
                    if (dto.getIsLocked() != null)    state.setIsLocked(Boolean.TRUE.equals(dto.getIsLocked()));
                }
                case CURTAIN -> {
                    if (dto.getPosition() != null)    state.setPosition(dto.getPosition());
                }
            }
        }

        deviceRepository.save(device);
    }
    private DeviceState buildDeviceState(Device device, DeviceType type, DeviceStateDto dto) {
        DeviceState state = new DeviceState();
        state.setDevice(device);

        switch (type) {
            case LIGHT -> {
                state.setBrightness(dto.getBrightness());
                state.setColor(dto.getColor());
            }
            case SPEAKER -> state.setVolume(dto.getVolume());
            case DOOR_LOCK -> state.setIsLocked(Boolean.TRUE.equals(dto.getIsLocked()));
            case CURTAIN -> state.setPosition(dto.getPosition());
        }

        return state;
    }



}
