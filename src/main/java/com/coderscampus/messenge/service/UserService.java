package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public User userExists(String username) {
        return userRepo.userExists(username);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

}
