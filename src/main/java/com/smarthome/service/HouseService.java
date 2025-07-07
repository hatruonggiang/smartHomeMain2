package com.smarthome.service;

import com.smarthome.dto.*;
import com.smarthome.model.*;
import com.smarthome.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private UserRepository userRepository;

    public House createHouse(HouseDTO houseDTO, String username) {
        House house = new House();
        house.setName(houseDTO.getName());
        house.setAddress(houseDTO.getAddress());
        house.setDescription(houseDTO.getDescription()); 
        house.setOwner(userRepository.findByUsername(username));
        return houseRepository.save(house);
    }

    public House updateHouse(Long id, HouseDTO houseDTO) {
        House house = houseRepository.findById(id).orElseThrow();
        house.setName(houseDTO.getName());
        house.setAddress(houseDTO.getAddress());
        house.setDescription(houseDTO.getDescription()); 
        return houseRepository.save(house);
    }

    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }

    public List<House> getHousesOwnedBy(String username) {
        return houseRepository.findAllByOwner_Username(username);
    }

    @Transactional
    public void addMember(Long houseId, Long userId) {
        House house = houseRepository.findById(houseId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        house.getMembers().add(user);
        houseRepository.save(house);
    }

    public HouseResponseDTO toDTO(House house) {
        HouseResponseDTO dto = new HouseResponseDTO();
        dto.setId(house.getId());
        dto.setName(house.getName());
        dto.setAddress(house.getAddress());
        dto.setDescription(house.getDescription()); 
        return dto;
    }
}
