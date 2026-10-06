package com.careersync.controller;

import com.careersync.model.User;
import com.careersync.security.UserPrincipal;
import com.careersync.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return profileService.getProfile(principal.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserPrincipal principal,
                                           @RequestBody User updatedUser) {
        try {
            User user = profileService.updateProfile(principal.getUserId(), updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
