package com.careersync.controller;

import com.careersync.model.Certificate;
import com.careersync.repository.CertificateRepository;
import com.careersync.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {
    private final CertificateRepository repo;
    public CertificateController(CertificateRepository repo) { this.repo = repo; }

    @GetMapping
    public ResponseEntity<List<Certificate>> get(@AuthenticationPrincipal UserPrincipal p) {
        return ResponseEntity.ok(repo.findByUserId(p.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Certificate> add(@AuthenticationPrincipal UserPrincipal p, @RequestBody Certificate c) {
        c.setUserId(p.getUserId());
        return ResponseEntity.ok(repo.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal UserPrincipal p, @PathVariable String id, @RequestBody Certificate updated) {
        return repo.findById(id).map(c -> {
            if (!c.getUserId().equals(p.getUserId())) return ResponseEntity.status(403).<Certificate>build();
            if (updated.getTitle() != null) c.setTitle(updated.getTitle());
            if (updated.getIssuer() != null) c.setIssuer(updated.getIssuer());
            if (updated.getIssueDate() != null) c.setIssueDate(updated.getIssueDate());
            if (updated.getCredentialUrl() != null) c.setCredentialUrl(updated.getCredentialUrl());
            if (updated.getSkills() != null) c.setSkills(updated.getSkills());
            return ResponseEntity.ok(repo.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal p, @PathVariable String id) {
        repo.findById(id).ifPresent(c -> { if (c.getUserId().equals(p.getUserId())) repo.delete(c); });
        return ResponseEntity.noContent().build();
    }
}
