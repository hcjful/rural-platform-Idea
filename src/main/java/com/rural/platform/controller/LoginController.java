package com.rural.platform.controller;

import com.rural.platform.dto.LoginRequest;
import com.rural.platform.dto.LoginResponse;
import com.rural.platform.dto.RegisterRequest;
import com.rural.platform.entity.User;
import com.rural.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081") // 允许前端访问
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token, request.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userService.register(user);
        return ResponseEntity.ok().body("注册成功");
    }
}