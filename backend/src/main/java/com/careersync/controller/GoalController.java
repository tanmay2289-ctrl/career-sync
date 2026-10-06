package com.careersync.controller;

import com.careersync.model.Goal;
import com.careersync.repository.GoalRepository;
import com.careersync.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {
    private final GoalRepository repo;
    public GoalController(GoalRepository repo) { this.repo = repo; }

    @GetMapping
    public ResponseEntity<List<Goal>> get(@AuthenticationPrincipal UserPrincipal p) {
        return ResponseEntity.ok(repo.findByUserId(p.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Goal> add(@AuthenticationPrincipal UserPrincipal p, @RequestBody Goal g) {
        g.setUserId(p.getUserId());
        if (g.getStatus() == null) g.setStatus("Active");
        if (g.getProgress() == null) g.setProgress(0);
        return ResponseEntity.ok(repo.save(g));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal UserPrincipal p, @PathVariable String id, @RequestBody Goal updated) {
        return repo.findById(id).map(g -> {
            if (!g.getUserId().equals(p.getUserId())) return ResponseEntity.status(403).<Goal>build();
            if (updated.getTitle() != null) g.setTitle(updated.getTitle());
            if (updated.getCategory() != null) g.setCategory(updated.getCategory());
            if (updated.getTargetDate() != null) g.setTargetDate(updated.getTargetDate());
            if (updated.getProgress() != null) g.setProgress(updated.getProgress());
            if (updated.getStatus() != null) g.setStatus(updated.getStatus());
            if (updated.getDescription() != null) g.setDescription(updated.getDescription());
            return ResponseEntity.ok(repo.save(g));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal p, @PathVariable String id) {
        repo.findById(id).ifPresent(g -> {
            if (g.getUserId().equals(p.getUserId())) repo.delete(g);
        });
        return ResponseEntity.noContent().build();
    }
}
