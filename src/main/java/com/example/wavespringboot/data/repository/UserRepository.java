package com.example.wavespringboot.data.repository;

import com.example.wavespringboot.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelephone(String telephone);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
    Optional<User> findByCodeVerification(String code);
}
