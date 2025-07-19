// AuthRequest.java
package com.devsync.devsync_backend.dto;

import lombok.*;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
