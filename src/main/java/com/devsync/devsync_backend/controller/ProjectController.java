package com.devsync.devsync_backend.controller;

import com.devsync.devsync_backend.dto.ProjectRequest;
import com.devsync.devsync_backend.model.Project;
import com.devsync.devsync_backend.model.User;
import com.devsync.devsync_backend.service.ProjectService;
import com.devsync.devsync_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Project> getProjectsByOwner(@PathVariable Long ownerId) {
        return projectService.getProjectsByOwner(ownerId);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        Optional<User> ownerOpt = userRepository.findById(request.getOwnerId());

        if (ownerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid ownerId: User not found.");
        }

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(ownerOpt.get())
                .status("ACTIVE")
                .build();

        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
