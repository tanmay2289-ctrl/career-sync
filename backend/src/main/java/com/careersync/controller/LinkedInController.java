package com.careersync.controller;

import com.careersync.model.LinkedInData;
import com.careersync.security.UserPrincipal;
import com.careersync.service.LinkedInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/linkedin")
public class LinkedInController {

    private final LinkedInService linkedInService;

    public LinkedInController(LinkedInService linkedInService) {
        this.linkedInService = linkedInService;
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserPrincipal p) {
        if (p == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return linkedInService.getByUserId(p.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal UserPrincipal p,
                                  @RequestBody LinkedInData data) {
        if (p == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(linkedInService.saveOrUpdate(p.getUserId(), data));
    }

    @PostMapping("/optimize")
    public ResponseEntity<?> optimize(@AuthenticationPrincipal UserPrincipal p) {
        if (p == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(linkedInService.optimizeWithAi(p.getUserId()));
    }
}
