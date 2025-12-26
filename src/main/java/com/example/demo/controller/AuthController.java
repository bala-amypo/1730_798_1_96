// package com.example.demo.controller;

// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.AuthResponse;
// import com.example.demo.service.AuthService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// @RequiredArgsConstructor
// public class AuthController {

//     private final AuthService authService;

//     @PostMapping("/login")
//     public AuthResponse login(@RequestBody AuthRequest request) {
//         return authService.login(request);
//     }
// }
