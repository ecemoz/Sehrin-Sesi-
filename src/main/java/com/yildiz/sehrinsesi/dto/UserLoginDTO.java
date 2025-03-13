package com.yildiz.sehrinsesi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {
    private String email;
    private String password;
}