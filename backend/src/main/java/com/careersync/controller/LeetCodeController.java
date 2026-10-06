package com.careersync.controller;

import com.careersync.model.LeetCodeData;
import com.careersync.security.UserPrincipal;
import com.careersync.service.LeetCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leetcode")
public class LeetCodeController {

    private final LeetCodeService leetCodeService;

    public LeetCodeController(LeetCodeService leetCodeService) {
        this.leetCodeService = leetCodeService;
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserPrincipal p) {
        return leetCodeService.getByUserId(p.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sync/{username}")
    public ResponseEntity<?> sync(@AuthenticationPrincipal UserPrincipal p, @PathVariable String username) {
        try {
            LeetCodeData data = leetCodeService.fetchAndSave(p.getUserId(), username);
            return ResponseEntity.ok(data);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
