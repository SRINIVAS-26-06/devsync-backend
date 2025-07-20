package com.devsync.devsync_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "ACTIVE";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Many projects can be owned by one user
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"ownedProjects", "assignedTasks"})  // avoid user recursion
    private User owner;

    // One project can have multiple sprints
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"project", "tasks"})  // avoid sprint → project → sprint loop
    private List<Sprint> sprints;

    // One project can have multiple tasks
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"project", "assignedTo", "sprint"})  // avoid task → project loop
    private List<Task> tasks;
}
