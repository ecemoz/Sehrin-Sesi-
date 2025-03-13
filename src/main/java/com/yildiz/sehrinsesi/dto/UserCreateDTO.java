package com.yildiz.sehrinsesi.dto;

import com.yildiz.sehrinsesi.utils.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole userRole;
    private String phoneNumber;
    private String address;
}
