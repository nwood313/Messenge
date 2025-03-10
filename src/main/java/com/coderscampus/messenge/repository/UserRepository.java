package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
