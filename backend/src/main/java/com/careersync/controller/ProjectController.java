package com.careersync.controller;

import com.careersync.model.Project;
import com.careersync.repository.ProjectRepository;
import com.careersync.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(projectRepository.findByUserId(principal.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@AuthenticationPrincipal UserPrincipal principal,
                                              @RequestBody Project project) {
        project.setUserId(principal.getUserId());
        return ResponseEntity.ok(projectRepository.save(project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@AuthenticationPrincipal UserPrincipal principal,
                                           @PathVariable String id, @RequestBody Project updated) {
        return projectRepository.findById(id).map(p -> {
            if (!p.getUserId().equals(principal.getUserId())) return ResponseEntity.status(403).<Project>build();
            if (updated.getName() != null) p.setName(updated.getName());
            if (updated.getDescription() != null) p.setDescription(updated.getDescription());
            if (updated.getTechnologies() != null) p.setTechnologies(updated.getTechnologies());
            if (updated.getGithubUrl() != null) p.setGithubUrl(updated.getGithubUrl());
            if (updated.getLiveUrl() != null) p.setLiveUrl(updated.getLiveUrl());
            if (updated.getRole() != null) p.setRole(updated.getRole());
            return ResponseEntity.ok(projectRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@AuthenticationPrincipal UserPrincipal principal,
                                              @PathVariable String id) {
        projectRepository.findById(id).ifPresent(p -> {
            if (p.getUserId().equals(principal.getUserId())) projectRepository.delete(p);
        });
        return ResponseEntity.noContent().build();
    }
}
