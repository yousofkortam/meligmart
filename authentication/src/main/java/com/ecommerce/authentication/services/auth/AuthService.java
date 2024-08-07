package com.ecommerce.authentication.services.auth;


import com.ecommerce.authentication.dto.AuthResponse;
import com.ecommerce.authentication.dto.LoginDto;
import com.ecommerce.authentication.dto.RegisterDto;

import java.security.Principal;

public interface AuthService {
    AuthResponse login(LoginDto loginDto);
    AuthResponse register(RegisterDto registerDto);
    void validateToken(String token, Principal principal);
}
