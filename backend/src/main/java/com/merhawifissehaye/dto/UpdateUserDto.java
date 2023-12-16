package com.merhawifissehaye.dto;

import com.merhawifissehaye.model.User;
import lombok.Data;

@Data
public class UpdateUserDto {
    private String email;
    private String name;
    private long balance;
    private String password;

    public User toUser() {
        return User.builder()
                .email(email)
                .name(name)
                .balance(balance)
                .password(password).build();
    }
}
