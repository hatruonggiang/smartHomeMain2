package com.smarthome.repository;

import com.smarthome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
    User findByResetToken(String resetToken);

}