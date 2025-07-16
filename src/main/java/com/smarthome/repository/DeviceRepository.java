package com.smarthome.repository;

import com.smarthome.model.Device;

import java.util.List;

import com.smarthome.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByRoomId(Long roomId);

    List<Device> findByRoom(Room room);
}