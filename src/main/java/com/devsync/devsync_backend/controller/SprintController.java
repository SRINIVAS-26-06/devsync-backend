package com.devsync.devsync_backend.controller;

import com.devsync.devsync_backend.model.Sprint;
import com.devsync.devsync_backend.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sprints")
@CrossOrigin(origins = "*")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @GetMapping
    public List<Sprint> getAllSprints() {
        return sprintService.getAllSprints();
    }

    @GetMapping("/{id}")
    public Optional<Sprint> getSprintById(@PathVariable Long id) {
        return sprintService.getSprintById(id);
    }

    @GetMapping("/project/{projectId}")
    public List<Sprint> getSprintsByProject(@PathVariable Long projectId) {
        return sprintService.getSprintsByProject(projectId);
    }

    @PostMapping
    public Sprint createSprint(@RequestBody Sprint sprint) {
        return sprintService.saveSprint(sprint);
    }

    @DeleteMapping("/{id}")
    public void deleteSprint(@PathVariable Long id) {
        sprintService.deleteSprint(id);
    }
}
