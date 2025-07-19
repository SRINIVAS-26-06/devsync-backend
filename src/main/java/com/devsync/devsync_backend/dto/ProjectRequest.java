// src/main/java/com/devsync/devsync_backend/dto/ProjectRequest.java

package com.devsync.devsync_backend.dto;

import lombok.Data;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private Long ownerId;
}
