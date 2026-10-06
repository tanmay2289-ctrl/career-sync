package com.careersync.controller;

import com.careersync.model.Skill;
import com.careersync.security.UserPrincipal;
import com.careersync.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getSkills(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(skillService.getSkills(principal.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Skill> addSkill(@AuthenticationPrincipal UserPrincipal principal,
                                          @RequestBody Skill skill) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(skillService.addSkill(principal.getUserId(), skill));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Skill>> addSkillsBatch(@AuthenticationPrincipal UserPrincipal principal,
                                                      @RequestBody List<String> skillNames) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(skillService.addSkillsBatch(principal.getUserId(), skillNames));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(@AuthenticationPrincipal UserPrincipal principal,
                                         @PathVariable String id,
                                         @RequestBody Skill skill) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            return ResponseEntity.ok(skillService.updateSkill(principal.getUserId(), id, skill));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@AuthenticationPrincipal UserPrincipal principal,
                                            @PathVariable String id) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        skillService.deleteSkill(principal.getUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
