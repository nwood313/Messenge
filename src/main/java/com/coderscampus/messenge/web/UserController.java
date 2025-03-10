package com.coderscampus.messenge.web;


import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping ("/users")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping ("/exists")
    @ResponseBody  //Tells spring to behave like a rest controller (returns an object rather than a view (model))
    public Boolean postExists (@RequestBody User user) {
        user = userService.findByUsername(user.getUsername());
        return (user!= null);
    }

    @GetMapping("/validateUsername")
    @ResponseBody
    public Boolean getValidUsername(String name) {
        return true;
    }
    @GetMapping("/validatePassword")
    @ResponseBody
    public Boolean getValidPassword(String password) {
        return true;
    }
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
}
