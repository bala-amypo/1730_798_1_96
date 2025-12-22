package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    public AuthServiceImpl(
            UserAccountRepository repo,
            BCryptPasswordEncoder encoder,) {
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        return new AuthResponse("dummy-token");
    }
}
