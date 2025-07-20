package com.devsync.devsync_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "sprints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String status = "PLANNED";

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"sprints", "tasks", "owner"})  // prevent project nesting
    private Project project;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"sprint", "project", "assignedTo"})  // avoid nesting through tasks
    private List<Task> tasks;
}
