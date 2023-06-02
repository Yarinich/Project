package ua.yarynych.backservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.yarynych.backservice.entity.car.User;
import ua.yarynych.backservice.service.AuthService;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public User login(@RequestHeader(value = "Authorization") String email) throws Exception {
        return authService.login(email);
    }

    @PostMapping("/registration")
    public User registration(@RequestBody User user) throws Exception {
        return authService.registration(user);
    }

    @GetMapping("/users")
    public Map<String, User> getUsers() {
        return authService.getUsers();
    }
}
