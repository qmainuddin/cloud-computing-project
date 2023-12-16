package com.merhawifissehaye.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

final public class Auth {
    @Data
    public static class AuthRequest {
        private String email;
        private String password;
        private String licenseNumber;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthResponse {
        private String email;
        private String accessToken;
    }
}
