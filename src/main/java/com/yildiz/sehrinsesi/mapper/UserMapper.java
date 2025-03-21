package com.yildiz.sehrinsesi.mapper;

import com.yildiz.sehrinsesi.dto.*;
import com.yildiz.sehrinsesi.model.Address;
import com.yildiz.sehrinsesi.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User fromCreateDto(UserCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserRole(dto.getUserRole());

        Address address = new Address();
        address.setStreet(dto.getAddress());
        user.setAddress(address);

        return user;
    }

    public UserResponseDTO toUserResponseDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(
                        user.getAddress() != null
                                ? user.getAddress().getStreet()
                                : null
                )
                .userRole(user.getUserRole())
                .build();
    }

    public User fromLoginDto(UserLoginDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public User fromUpdateDto(User user, UserUpdateDTO dto) {
        if (user == null || dto == null) {
            return user;
        }

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getAddress() != null) {
            if (user.getAddress() == null) {
                user.setAddress(new Address());
            }
            user.getAddress().setStreet(dto.getAddress());
        }

        return user;
    }

    public List<UserResponseDTO> toUserResponseDtoList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(this::toUserResponseDto)
                .collect(Collectors.toList());
    }
}
