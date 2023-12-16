package com.merhawifissehaye.service;

import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }
}
