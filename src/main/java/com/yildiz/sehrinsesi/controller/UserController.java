package com.yildiz.sehrinsesi.controller;

import com.yildiz.sehrinsesi.dto.*;
import com.yildiz.sehrinsesi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        UserResponseDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/phone")
    public ResponseEntity<UserResponseDTO> updatePhone(@PathVariable Long id, @RequestParam String phoneNumber) {
        UserResponseDTO updatedUser = userService.updateUserPhoneNumber(id, phoneNumber);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/username")
    public ResponseEntity<List<UserResponseDTO>> getByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserResponseDTO>> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/search/phone")
    public ResponseEntity<List<UserResponseDTO>> getByPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.findUserByPhoneNumber(phoneNumber));
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<UserResponseDTO>> getByAddressId(@RequestParam Long addressId) {
        return ResponseEntity.ok(userService.findUserByAddressId(addressId));
    }

    @GetMapping("/exists/username")
    public ResponseEntity<Boolean> existsByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }

    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}
