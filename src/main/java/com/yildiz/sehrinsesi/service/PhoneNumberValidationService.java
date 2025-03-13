package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.utils.PhoneNumberUtil;

public class PhoneNumberValidationService {
    public String validatePhoneNumber(String phoneNumber) {
        if(!PhoneNumberUtil.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        return phoneNumber;
    }
}