package com.merhawifissehaye.controller;

import com.merhawifissehaye.dto.UpdateUserDto;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    User getMe(@AuthenticationPrincipal String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @PutMapping("/me")
    User updateMe(@AuthenticationPrincipal String email, @Valid @RequestBody UpdateUserDto userDto) {
        var userToUpdate = userRepository.findByEmail(email).orElseThrow();
        var requestUser = userDto.toUser();
        userToUpdate.setName(requestUser.getName());
        userToUpdate.setEmail(requestUser.getEmail());
        userToUpdate.setBalance(requestUser.getBalance());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) userToUpdate.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );
        return userRepository.save(userToUpdate);
    }
}
