package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User save (User user) {
        return userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
