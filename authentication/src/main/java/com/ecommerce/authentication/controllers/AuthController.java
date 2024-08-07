package com.ecommerce.authentication.controllers;

import com.ecommerce.authentication.dto.AuthResponse;
import com.ecommerce.authentication.dto.LoginDto;
import com.ecommerce.authentication.dto.RegisterDto;
import com.ecommerce.authentication.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/token/validation")
    public void validateToken(@RequestHeader("Authorization") String token, Principal principal) {
        authService.validateToken(token, principal);
    }

}
