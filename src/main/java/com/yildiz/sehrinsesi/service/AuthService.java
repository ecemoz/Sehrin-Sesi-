package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.dto.UserCreateDTO;
import com.yildiz.sehrinsesi.dto.UserLoginDTO;
import com.yildiz.sehrinsesi.dto.UserResponseDTO;
import com.yildiz.sehrinsesi.exception.UserAlreadyExistsException;
import com.yildiz.sehrinsesi.mapper.UserMapper;
import com.yildiz.sehrinsesi.model.Address;
import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.UserRepository;
import com.yildiz.sehrinsesi.security.JwtUtil;
import com.yildiz.sehrinsesi.utils.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public String registerUser(UserCreateDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Kullanıcı adı zaten kullanımda!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email zaten kullanımda!");
        }

        User user = userMapper.fromCreateDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // şifre hashlenmeli
        user.setUserRole(dto.getUserRole() != null ? dto.getUserRole() : UserRole.USER); // default

        Address address = new Address();
        address.setStreet(dto.getAddress());
        user.setAddress(address);

        userRepository.save(user);
        return "Kullanıcı başarıyla kaydedildi!";
    }

    public String loginUser(UserLoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + dto.getEmail()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Şifre hatalı!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }


    public UserResponseDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Yetkilendirilmiş kullanıcı bulunamadı.");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));

        return userMapper.toUserResponseDto(user);
    }
}
