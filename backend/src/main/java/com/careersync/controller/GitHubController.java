package com.careersync.controller;

import com.careersync.model.GitHubData;
import com.careersync.security.UserPrincipal;
import com.careersync.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/github")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserPrincipal p) {
        return gitHubService.getByUserId(p.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sync/{username}")
    public ResponseEntity<?> sync(@AuthenticationPrincipal UserPrincipal p, @PathVariable String username) {
        try {
            GitHubData data = gitHubService.fetchAndSave(p.getUserId(), username);
            return ResponseEntity.ok(data);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
