package com.smarthome.repository;

import com.smarthome.model.Device;

import java.util.List;
import java.util.Optional;

import com.smarthome.model.DeviceState;
import com.smarthome.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStateRepository extends JpaRepository<DeviceState, Long> {

    Optional<DeviceState> findByDevice(Device device);

}