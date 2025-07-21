package com.devsync.devsync_backend.dto;

import com.devsync.devsync_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private User user; // Or replace with a custom DTO for minimal info
}