package com.invoicing.invoicing.controller;

import com.invoicing.invoicing.exception.ResourceNotFoundException;
import com.invoicing.invoicing.model.Task;
import com.invoicing.invoicing.repository.ProjectRepository;
import com.invoicing.invoicing.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getTasksByProjectId(@PathVariable Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public Task addTask(@PathVariable Long projectId,
                            @Valid @RequestBody Task task) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    task.setProject(project);

                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    }

    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    public Task updateTask(@PathVariable Long projectId,
                               @PathVariable Long taskId,
                               @Valid @RequestBody Task taskRequest) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }

        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setLabel(taskRequest.getLabel());
                    task.setCost(taskRequest.getCost());
                    task.setStartedAt(taskRequest.getStartedAt());
                    task.setStoppedAt(taskRequest.getStoppedAt());
                    task.setProject(taskRequest.getProject());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
    }

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long projectId,
                                          @PathVariable Long taskId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }

        return taskRepository.findById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

    }
}
