package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.dto.UserResponseDTO;
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

    public UserService(UserRepository userRepository, PhoneNumberValidationService phoneNumberValidationService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.phoneNumberValidationService = phoneNumberValidationService;
        this.userMapper = userMapper;
    }

    public UserResponseDTO updateUserPhoneNumber(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        phoneNumberValidationService.validatePhoneNumber(phoneNumber);
        user.setPhoneNumber(phoneNumber);
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(updatedUser);
    }

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("No users found in the system.");
        }
        return userMapper.toUserResponseDtoList(users);
    }




    public List<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findUserByAddressId(Long addressId) {
        return userRepository.findUserByAddressId(addressId);
    }

    public List<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUsersByPhoneNumber(phoneNumber);
    }

    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
