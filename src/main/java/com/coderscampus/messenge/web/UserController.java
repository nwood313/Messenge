package com.coderscampus.messenge.web;


import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/sso-login")
    public String getRegister(ModelMap model) {
        User user = new User();
        model.put("user", user);

        return "sso-logins";
    }
    @PostMapping ("/sso-logins")
    public String postRegister (User user) {
        userService.save(user);
        return "redirect:/sso-logins";
    }

    @PostMapping("/users")
    public User createUser (@RequestBody String name, String password) {
        return userService.createUser(name, password);
    }

    @PostMapping("/channel")
    public String handleChannelSelection(@RequestParam String username, 
                                       @RequestParam String password,
                                       @RequestParam String channel) {
        // Here you would typically save the user and their channel selection
        // For now, we'll just redirect to the channel page
        return "channel";
    }
}
