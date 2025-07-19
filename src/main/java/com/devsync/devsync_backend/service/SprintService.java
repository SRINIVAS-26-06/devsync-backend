package com.devsync.devsync_backend.service;

import com.devsync.devsync_backend.model.Sprint;
import com.devsync.devsync_backend.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Optional<Sprint> getSprintById(Long id) {
        return sprintRepository.findById(id);
    }

    public List<Sprint> getSprintsByProject(Long projectId) {
        return sprintRepository.findByProjectId(projectId);
    }

    public Sprint saveSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public void deleteSprint(Long id) {
        sprintRepository.deleteById(id);
    }
}
