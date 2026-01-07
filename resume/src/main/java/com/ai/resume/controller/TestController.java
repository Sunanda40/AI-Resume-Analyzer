package com.ai.resume.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.resume.dto.ApiResponse;
import com.ai.resume.dto.LoginRequest;
import com.ai.resume.entity.User;
import com.ai.resume.security.JwtUtil;

import com.ai.resume.service.UserService;

@RestController
@RequestMapping("/api")
public class TestController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // ✅ ADD THIS CONSTRUCTOR HERE
    public TestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String home() {
        return "AI Resume Analyzer Project Started Successfully";
    }

    @PostMapping("/register")
    public ApiResponse registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ApiResponse("User Registered Successfully", true);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );

        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail());

            return ResponseEntity.ok(
                Map.of(
                    "message", "Login Successful",
                    "token", token
                )
            );
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid Email or Password", false));
        }
    }
}