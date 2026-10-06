package com.careersync.controller;

import com.careersync.model.Job;
import com.careersync.repository.JobRepository;
import com.careersync.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobRepository repo;
    public JobController(JobRepository repo) { this.repo = repo; }

    @GetMapping
    public ResponseEntity<List<Job>> get(@AuthenticationPrincipal UserPrincipal p) {
        return ResponseEntity.ok(repo.findByUserId(p.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Job> add(@AuthenticationPrincipal UserPrincipal p, @RequestBody Job j) {
        j.setUserId(p.getUserId());
        return ResponseEntity.ok(repo.save(j));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal UserPrincipal p, @PathVariable String id, @RequestBody Job updated) {
        return repo.findById(id).map(j -> {
            if (!j.getUserId().equals(p.getUserId())) return ResponseEntity.status(403).<Job>build();
            if (updated.getCompany() != null) j.setCompany(updated.getCompany());
            if (updated.getRole() != null) j.setRole(updated.getRole());
            if (updated.getLocation() != null) j.setLocation(updated.getLocation());
            if (updated.getJobUrl() != null) j.setJobUrl(updated.getJobUrl());
            if (updated.getStatus() != null) j.setStatus(updated.getStatus());
            if (updated.getNotes() != null) j.setNotes(updated.getNotes());
            if (updated.getApplicationDate() != null) j.setApplicationDate(updated.getApplicationDate());
            if (updated.getDeadline() != null) j.setDeadline(updated.getDeadline());
            return ResponseEntity.ok(repo.save(j));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal p, @PathVariable String id) {
        repo.findById(id).ifPresent(j -> { if (j.getUserId().equals(p.getUserId())) repo.delete(j); });
        return ResponseEntity.noContent().build();
    }
}
