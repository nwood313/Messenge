package com.coderscampus.messenge.web;

import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.ChannelService;
import com.coderscampus.messenge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ChannelService channelService;

    public UserController(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    @GetMapping("/")
    public String redirectToWelcomePage(HttpSession session) {
        // If user is already logged in, redirect to channels
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "redirect:/channels";
        }
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String getWelcomePage(HttpSession session, ModelMap model) {
        // Check if user is already logged in
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        // Add channels for dropdown
        model.addAttribute("channels", channelService.getAllChannels());
        return "welcome";
    }

    @PostMapping("/welcome")
    public String postWelcomePage() {
        return "redirect:/channel";
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
        if (existingUser != null) {
            return existingUser;
        }
        return userService.save(user);
    }

    @PostMapping("/channels")
    public String handleUserRegistration(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Long channelId,
            HttpSession session,
            ModelMap model) {

        if (username != null && password != null) {
            // Check if user exists
            User user = userService.userExists(username);
            if (user == null) {
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userService.save(user);
            }

            // Store user in session
            session.setAttribute("user", user);
            model.addAttribute("user", user);

            // Redirect to the selected channel
            return "redirect:/channels/" + channelId;
        }

        return "redirect:/welcome";
    }

    @GetMapping("/channel")
    public String getChannelPage(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("user", user);
        return "channel";
    }
}
