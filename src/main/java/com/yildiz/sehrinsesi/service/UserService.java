package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
