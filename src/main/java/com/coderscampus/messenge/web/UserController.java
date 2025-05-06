package com.coderscampus.messenge.web;


import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectToWelcomePage() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String getWelcomePage() {
        return "welcome";
    }

    @PostMapping("/welcome")
    public String postWelcomePage() {
        return "redirect:/channels";
    }

    @ResponseBody
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @ResponseBody
    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        User existingUser = userService.userExists(user.getUsername());
        if(existingUser != null) {
            return existingUser;
        }
        return userService.save(user);
    }
}
