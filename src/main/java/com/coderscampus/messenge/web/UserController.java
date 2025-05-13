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

    @PostMapping("/channel")
    public String handleChannelPost(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String channel, ModelMap model) {
        if (username != null && password != null) {
            // Check if user exists in the database
            User existingUser = userService.userExists(username);
            User user;

            if (existingUser != null) {
                // Use existing user data
                user = existingUser;
            } else {
                // Create a new user with default values
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setFirstName("User");
                user.setLastName(username);
                // Optionally save the new user
                // userService.save(user);
            }

            model.addAttribute("user", user);
            // Redirect to channels endpoint which will load the channel data
            return "redirect:/channels";
        }
        return "redirect:/welcome";
    }

    @GetMapping("/channel")
    public String getChannelPage(ModelMap model) {
        User user = (User) model.get("user");
        if (user == null) {
            return "redirect:/welcome";
        }
        return "channel";
    }
}
