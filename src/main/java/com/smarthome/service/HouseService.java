package com.smarthome.service;

import com.smarthome.dto.*;
import com.smarthome.enums.HouseMemberRole;
import com.smarthome.exception.ResourceNotFoundException;
import com.smarthome.model.*;
import com.smarthome.repository.*;

import com.smarthome.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMemberRepository houseMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;





    public List<HouseResponse> getAllHouses(String token) {
        User currentUser = authService.getCurrentUser(token);
        List<HouseMember> memberships = houseMemberRepository.findByUserId(currentUser.getId());

        return memberships.stream()
                .map(hm -> new HouseResponse(
                        hm.getHouse().getId(),
                        hm.getHouse().getName(),
                        hm.getHouse().getAddress(),
                        hm.getHouse().getDescription(),
                        hm.getRole()
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public List<Room> getRoomsOfHouseIfMember(String token, Long houseId) {
        User currentUser = authService.getCurrentUser(token);

        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà"));

        boolean isMember = houseMemberRepository.existsByUserIdAndHouseId(currentUser.getId(), houseId);
        if (!isMember) {
            throw new AccessDeniedException("Bạn không phải thành viên của nhà này");
        }

        return house.getRooms();
    }



    @Transactional
    public void createHouse(String token, CreateHouseRequest request) {
        User currentUser = authService.getCurrentUser(token);

        // Tạo đối tượng House
        House house = new House();
        house.setName(request.getName());
        house.setAddress(request.getAddress());
        house.setDescription(request.getDescription());
        house.setOwner(currentUser);

        // Lưu house vào DB
        House savedHouse = houseRepository.save(house);

        HouseMember ownerMember = HouseMember.builder()
                .house(savedHouse)
                .user(currentUser)
                .role(HouseMemberRole.OWNER)
                .build();

        houseMemberRepository.save(ownerMember);

    }


    @Transactional
    public void addMemberToHouse(String token, AddMemberRequest request) {
        User inviter = authService.getCurrentUser(token);

        House house = houseRepository.findById(request.getHouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà với ID: " + request.getHouseId()));

        User newMember = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + request.getUserId()));
        validateAddMemberRequest(inviter, house, newMember);

        HouseMember houseMember = buildMemberHouseFrom(token, request);
        houseMemberRepository.save(houseMember);
    }

    @Transactional
    public void addRoomToHouse(String token, Long id, AddRoomRequest request) {
        User user = authService.getCurrentUser(token);

        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà"));

        validateHouseOwnership(user, house);

        Room newRoom = new Room();
        newRoom.setName(request.getName());
        newRoom.setDescription(request.getDescription());
        newRoom.setHouse(house);

        roomRepository.save(newRoom);
    }

    @Transactional
    public void deleteHouse(String token, Long houseId) {
        User currentUser = authService.getCurrentUser(token);

        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà với ID: " + houseId));

        validateHouseOwnership(currentUser, house);

        houseRepository.delete(house);
    }


    @Transactional
    public void updateHouse(String token, Long houseId, CreateHouseRequest request) {
        User currentUser = authService.getCurrentUser(token);

        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà"));

        validateHouseOwnership(currentUser, house);

        house.setName(request.getName());
        house.setAddress(request.getAddress());
        house.setDescription(request.getDescription());

        houseRepository.save(house);
    }



    //validation
    private void validateAddMemberRequest(User inviter, House house, User newMember) {
        validateHouseOwnership(inviter, house);

        boolean isMemberExist = house.getMembers().stream()
                .anyMatch(user -> user.getId().equals(newMember.getId()));
        if (isMemberExist) {
            try {
                throw new BadRequestException("Người dùng đã là thành viên.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //Builder
    private HouseMember buildMemberHouseFrom(String token, AddMemberRequest request) {
        User inviter = authService.getCurrentUser(token);
        House house = houseRepository.findById(request.getHouseId()).get();
        User addMember = userRepository.findById(request.getUserId()).get();
        return HouseMember.builder()
                .house(house)
                .user(addMember)
                .role(request.getRole() != null ? request.getRole() : HouseMemberRole.MEMBER)
                .invitedBy(inviter)
                .joinedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    //validation
    private void validateHouseOwnership(User user, House house) {
        if (!house.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("Bạn không phải là chủ sở hữu của nhà");
        }
    }



}
