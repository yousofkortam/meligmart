package com.ecommerce.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    String email;
    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is required")
    @Min(value = 8, message = "Password must be at least 8 characters long")
    String password;

}
