package com.careersync.controller;

import com.careersync.repository.*;
import com.careersync.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;
    private final CertificateRepository certificateRepository;
    private final JobRepository jobRepository;
    private final GoalRepository goalRepository;
    private final ResumeRepository resumeRepository;

    public AnalyticsController(SkillRepository skillRepo, ProjectRepository projectRepo,
                               CertificateRepository certRepo, JobRepository jobRepo,
                               GoalRepository goalRepo, ResumeRepository resumeRepo) {
        this.skillRepository = skillRepo;
        this.projectRepository = projectRepo;
        this.certificateRepository = certRepo;
        this.jobRepository = jobRepo;
        this.goalRepository = goalRepo;
        this.resumeRepository = resumeRepo;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAnalytics(@AuthenticationPrincipal UserPrincipal p) {
        String userId = p.getUserId();
        Map<String, Object> analytics = new HashMap<>();

        analytics.put("skillsCount", skillRepository.findByUserId(userId).size());
        analytics.put("projectsCount", projectRepository.findByUserId(userId).size());
        analytics.put("certificatesCount", certificateRepository.findByUserId(userId).size());
        analytics.put("totalJobs", jobRepository.findByUserId(userId).size());
        analytics.put("appliedJobs", jobRepository.countByUserIdAndStatus(userId, "Applied"));
        analytics.put("shortlistedJobs", jobRepository.countByUserIdAndStatus(userId, "Shortlisted"));
        analytics.put("selectedJobs", jobRepository.countByUserIdAndStatus(userId, "Selected"));
        analytics.put("goalsCount", goalRepository.findByUserId(userId).size());
        analytics.put("completedGoals", goalRepository.findByUserIdAndStatus(userId, "Completed").size());
        analytics.put("hasResume", resumeRepository.findTopByUserIdOrderByUploadedAtDesc(userId).isPresent());

        // Skills by category
        Map<String, Long> skillsByCategory = new HashMap<>();
        skillRepository.findByUserId(userId).forEach(s -> {
            String cat = s.getCategory() != null ? s.getCategory() : "Other";
            skillsByCategory.merge(cat, 1L, Long::sum);
        });
        analytics.put("skillsByCategory", skillsByCategory);

        // Job statuses
        Map<String, Long> jobByStatus = new HashMap<>();
        jobRepository.findByUserId(userId).forEach(j -> {
            String status = j.getStatus() != null ? j.getStatus() : "Unknown";
            jobByStatus.merge(status, 1L, Long::sum);
        });
        analytics.put("jobsByStatus", jobByStatus);

        // Goals progress average
        double avgGoalProgress = goalRepository.findByUserId(userId).stream()
                .mapToInt(g -> g.getProgress() != null ? g.getProgress() : 0)
                .average().orElse(0);
        analytics.put("avgGoalProgress", Math.round(avgGoalProgress));

        return ResponseEntity.ok(analytics);
    }
}
