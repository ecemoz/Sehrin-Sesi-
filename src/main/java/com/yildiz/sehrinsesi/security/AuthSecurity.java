package com.yildiz.sehrinsesi.security;

import com.yildiz.sehrinsesi.model.Complaints;
import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.ComplaintsRepository;
import com.yildiz.sehrinsesi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("authSecurity")
@RequiredArgsConstructor
public class AuthSecurity {

    private final UserRepository userRepository;
    private final ComplaintsRepository complaintsRepository;

    public boolean isSelf(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .stream()
                .findFirst()
                .orElse(null);

        return user != null && user.getId().equals(id);
    }

    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .stream()
                .findFirst()
                .map(User::getId)
                .orElse(null);
    }

    public boolean isComplaintOwner(Long complaintId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .stream()
                .findFirst()
                .orElse(null);

        if (user == null) {
            return false;
        }

        Complaints complaint = complaintsRepository.findById(complaintId)
                .orElse(null);

        return complaint != null && complaint.getUser().getId().equals(user.getId());
    }
}