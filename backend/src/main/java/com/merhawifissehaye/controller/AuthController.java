package com.merhawifissehaye.controller;

import com.merhawifissehaye.dto.RegisterUserDto;
import com.merhawifissehaye.model.Auth;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.service.UserService;
import com.merhawifissehaye.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Auth.AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            User u = (User) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(u);
            return ResponseEntity.ok(new Auth.AuthResponse(u.getEmail(), token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody RegisterUserDto u) {
        User user = userService.save(u.toUser());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
