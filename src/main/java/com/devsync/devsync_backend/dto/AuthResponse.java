package com.devsync.devsync_backend.dto;

import com.devsync.devsync_backend.model.User;
import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private User user;
}
