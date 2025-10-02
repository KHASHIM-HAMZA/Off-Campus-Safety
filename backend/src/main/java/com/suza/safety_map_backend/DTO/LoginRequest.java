package com.suza.safety_map_backend.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String identifier; // can be regNumber or username
    private String password;
}

