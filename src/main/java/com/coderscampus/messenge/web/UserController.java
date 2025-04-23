package com.coderscampus.messenge.web;


import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public User createUser (@RequestBody String name) {
        return userService.createUser(name);
    }
}
