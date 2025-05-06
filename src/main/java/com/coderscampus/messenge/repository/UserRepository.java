package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.User;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Component
public class UserRepository {

    private Set<User> users = new TreeSet<>();

    public User save (User user) {
        if (users.size() == 0) {
            user.setUserId(1L);
        } else {
            User lastUser = ((TreeSet<User>)users).last();
            user.setUserId(lastUser.getUserId() + 1L);
        }
        users.add(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
    return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
