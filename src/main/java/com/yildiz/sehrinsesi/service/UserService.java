package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.dto.*;
import com.yildiz.sehrinsesi.mapper.UserMapper;
import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhoneNumberValidationService phoneNumberValidationService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       PhoneNumberValidationService phoneNumberValidationService,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.phoneNumberValidationService = phoneNumberValidationService;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createUser(UserCreateDTO dto) {

        User user = userMapper.fromCreateDto(dto);

         if (existsByEmail(dto.getEmail())) {
           throw new RuntimeException("Email already in use: " + dto.getEmail());
         }

        phoneNumberValidationService.validatePhoneNumber(user.getPhoneNumber());

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(savedUser);
    }

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("No users found in the system.");
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDTO updateUser(Long userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        userMapper.fromUpdateDto(user, dto);
        phoneNumberValidationService.validatePhoneNumber(user.getPhoneNumber());
        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(updatedUser);
    }

    public UserResponseDTO updateUserPhoneNumber(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        phoneNumberValidationService.validatePhoneNumber(phoneNumber);
        user.setPhoneNumber(phoneNumber);

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(updatedUser);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public List<UserResponseDTO> findUserByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDTO> findUserByAddressId(Long addressId) {
        List<User> users = userRepository.findUserByAddressId(addressId);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDTO> findUserByPhoneNumber(String phoneNumber) {
        List<User> users = userRepository.findUsersByPhoneNumber(phoneNumber);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDTO> findUserByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        return userMapper.toUserResponseDtoList(users);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
