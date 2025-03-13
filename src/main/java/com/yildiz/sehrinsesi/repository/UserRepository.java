package com.yildiz.sehrinsesi.repository;

import com.yildiz.sehrinsesi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User>findAllUsers();
    List<User>findUsersByPhoneNumber(String phoneNumber);
    List<User> findByEmail(String email);
    List<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User>findUserByAddressId(Long addressId);
}