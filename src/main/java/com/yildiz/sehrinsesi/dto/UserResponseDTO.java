package com.yildiz.sehrinsesi.dto;

import com.yildiz.sehrinsesi.utils.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private UserRole userRole;
}
