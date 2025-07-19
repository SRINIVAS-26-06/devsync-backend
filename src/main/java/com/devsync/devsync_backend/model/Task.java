package com.devsync.devsync_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "TO_DO";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Task assigned to a user
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    // Task belongs to a project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // Task is part of a sprint
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
