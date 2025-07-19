package com.devsync.devsync_backend.repository;

import com.devsync.devsync_backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long userId);
    List<Task> findByProjectId(Long projectId);
    List<Task> findBySprintId(Long sprintId);
}
