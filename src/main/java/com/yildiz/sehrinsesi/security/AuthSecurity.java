package com.yildiz.sehrinsesi.security;

import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("authSecurity")
@RequiredArgsConstructor
public class AuthSecurity {

    private final UserRepository userRepository;

    public boolean isSelf(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .stream()
                .findFirst()
                .orElse(null);

        return user != null && user.getId().equals(id);
    }
}