package com.smarthome.controller;

import com.smarthome.dto.*;
import com.smarthome.exception.AuthenticationException;
import com.smarthome.exception.ResourceNotFoundException;
import com.smarthome.model.Room;
import com.smarthome.service.HouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    private static final Logger log = LoggerFactory.getLogger(HouseController.class);


    @GetMapping
    public ResponseEntity<ApiResponse<List<HouseResponse>>> getAllHouses(
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            List<HouseResponse> houses = houseService.getAllHouses(token);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Lấy danh sách nhà thành công", houses));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi lấy danh sách nhà", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }
    @PostMapping
    public ResponseEntity<ApiResponse> createHouse(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody CreateHouseRequest request) {
        try {
            String token = extractToken(authHeader);
            houseService.createHouse(token,request);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Đã tạo thêm nhà mới thành công",null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi tạo nhà", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }


    @GetMapping("/{id}/rooms")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getRoomsByHouseId(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        try {
            String token = extractToken(authHeader);
            List<Room> rooms = houseService.getRoomsOfHouseIfMember(token, id);

            List<RoomResponse> result = rooms.stream()
                    .map(room -> new RoomResponse(room.getId(), room.getName()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách phòng thành công", result));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(false, "Bạn không phải thành viên của nhà này", null));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Token không hợp lệ", null));
        }
    }




    @PostMapping("/members")
    public ResponseEntity<ApiResponse<?>> addMemberToHouse(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody AddMemberRequest request) {
        try {
            String token = extractToken(authHeader);
            houseService.addMemberToHouse(token, request);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Thêm thành viên thành công", null));
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi thêm thành viên", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }
    @PostMapping("/{id}/rooms")
    public ResponseEntity<ApiResponse<?>> addRoomToHouse(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @Valid @RequestBody AddRoomRequest request) {
        try {
            String token = extractToken(authHeader);
            houseService.addRoomToHouse(token,id, request);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Thêm phòng thành công", null));
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi thêm phòng", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống khi thêm phòng", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse>  updateHouse(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @Valid @RequestBody CreateHouseRequest request
            ) {
        try {
            String token = extractToken(authHeader);
            houseService.updateHouse(token,id, request);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Bạn đã sửa đổi nhà thành công",null));
        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(false, e.getMessage(),null));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi update house: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống", null));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteHouse(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        try {
            String token = extractToken(authHeader);

            houseService.deleteHouse(token, id);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Xóa nhà thành công"));
        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            log.error("Lỗi hệ thống khi delete house: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi hệ thống khi delete house", null));
        }
    }

    private String extractToken(String header) {
        if (header == null) {
            throw new RuntimeException("Thiếu Authorization header");
        }

        String trimmed = header.trim();
        if (trimmed.toLowerCase().startsWith("bearer ")) {
            return trimmed.substring(7).trim();
        }

        throw new RuntimeException("Authorization header không hợp lệ (phải bắt đầu bằng Bearer )");
    }


}