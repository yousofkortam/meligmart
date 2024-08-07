package com.ecommerce.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterDto {

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    String firstName;
    String middleName;
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    String lastName;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

}
