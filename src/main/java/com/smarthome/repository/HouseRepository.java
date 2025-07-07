package com.smarthome.repository;

import com.smarthome.model.House;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByOwner_Username(String username);

}