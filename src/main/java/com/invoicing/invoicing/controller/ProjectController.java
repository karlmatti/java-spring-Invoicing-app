package com.invoicing.invoicing.controller;

import com.invoicing.invoicing.exception.ResourceNotFoundException;
import com.invoicing.invoicing.model.Project;
import com.invoicing.invoicing.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Component
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public Page<Project> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @PostMapping("/projects")
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @PutMapping("/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId,
                                   @Valid @RequestBody Project projectRequest) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    project.setLabel(projectRequest.getLabel());
                    project.setDescription(projectRequest.getDescription());
                    return projectRepository.save(project);
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    projectRepository.delete(project);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    }

}
