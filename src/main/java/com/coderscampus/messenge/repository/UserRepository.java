package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public User userExists(String username) {
        return users.stream().filter(user -> Objects.equals(user.getUserId(), username)).findFirst().orElse(null);
    }
}