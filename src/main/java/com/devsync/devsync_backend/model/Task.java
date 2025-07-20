package com.devsync.devsync_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    @JsonIgnoreProperties({"assignedTasks", "ownedProjects"})  // avoid nested user-task-project loops
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"tasks", "sprints", "owner"})  // avoid nested project looping
    private Project project;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    @JsonIgnoreProperties("tasks")  // avoid task-sprint-task loop
    private Sprint sprint;
}
