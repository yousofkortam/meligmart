package com.ecommerce.authentication.services.auth;

import com.ecommerce.authentication.config.JwtService;
import com.ecommerce.authentication.dto.AuthResponse;
import com.ecommerce.authentication.dto.LoginDto;
import com.ecommerce.authentication.dto.RegisterDto;
import com.ecommerce.authentication.enums.Role;
import com.ecommerce.authentication.model.User;
import com.ecommerce.authentication.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return generateJwtToken(loginDto.getEmail());
    }

    @Override
    public AuthResponse register(RegisterDto registerDto) {
        boolean isEmailExist = userService.exists(registerDto.getEmail());
        if (isEmailExist) {
            throw new IllegalArgumentException("Email already exists");
        }
        userService.save(
                User.builder()
                        .firstName(registerDto.getFirstName())
                        .middleName(registerDto.getMiddleName())
                        .lastName(registerDto.getLastName())
                        .email(registerDto.getEmail())
                        .password(passwordEncoder.encode(registerDto.getPassword()))
                        .role(Role.CUSTOMER)
                        .build()
        );
        return generateJwtToken(registerDto.getEmail());
    }

    @Override
    public void validateToken(String token, Principal principal) {
        boolean isValidToken = jwtService.isTokenValid(token, (UserDetails) principal);
        if (!isValidToken) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    private AuthResponse generateJwtToken(String email) {
        return AuthResponse.builder()
                .code(HttpStatus.OK.value())
                .token(jwtService.generateToken(email))
                .build();
    }

}
