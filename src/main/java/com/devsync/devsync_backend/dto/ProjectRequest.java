package com.devsync.devsync_backend.dto;

import lombok.Data;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private Long ownerId;
    private String startDate;  // Assuming you're receiving it as a string from Angular (yyyy-MM-dd)
    private String endDate;
}
