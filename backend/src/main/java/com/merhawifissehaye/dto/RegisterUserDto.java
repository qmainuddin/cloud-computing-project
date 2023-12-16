package com.merhawifissehaye.dto;

import com.merhawifissehaye.model.User;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String email;

    @NotNull(message = "License number is required")
    @Size(min = 10, max = 100, message = "License number must be between 10 and 100 characters")
    private String licenseNumber;

    @NotNull(message = "Password is required")
    private String password;

    public User toUser() {
        return User.builder()
                .email(email)
                .password(password)
                .licenseNumber(licenseNumber).build();
    }
}
