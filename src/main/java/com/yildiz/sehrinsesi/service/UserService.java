package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.dto.UserResponseDTO;
import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private PhoneNumberValidationService phoneNumberValidationService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO updateUserPhoneNumber(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        phoneNumberValidationService.validatePhoneNumber(phoneNumber);
        user.setPhoneNumber(phoneNumber);
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(updatedUser);
    }

    public List<User>findAllUsers() {
        return userRepository.findAllUsers();
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
