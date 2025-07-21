package com.devsync.devsync_backend.controller;

import com.devsync.devsync_backend.dto.ProjectRequest;
import com.devsync.devsync_backend.model.Project;
import com.devsync.devsync_backend.model.User;
import com.devsync.devsync_backend.service.ProjectService;
import com.devsync.devsync_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'DEVELOPER', 'USER')")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Optional<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public List<Project> getProjectsByOwner(@PathVariable Long ownerId) {
        return projectService.getProjectsByOwner(ownerId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'DEVELOPER')")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        Optional<User> ownerOpt = userRepository.findById(request.getOwnerId());

        if (ownerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid ownerId: User not found.");
        }

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startDate(parseDate(request.getStartDate()))
                .endDate(parseDate(request.getEndDate()))
                .owner(ownerOpt.get())
                .status("ACTIVE")
                .build();

        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }
}
