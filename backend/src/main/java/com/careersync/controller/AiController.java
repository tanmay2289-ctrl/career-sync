package com.careersync.controller;

import com.careersync.model.AiAnalysis;
import com.careersync.model.Resume;
import com.careersync.model.Skill;
import com.careersync.repository.ResumeRepository;
import com.careersync.repository.SkillRepository;
import com.careersync.security.UserPrincipal;
import com.careersync.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;
    private final ResumeRepository resumeRepository;
    private final SkillRepository skillRepository;

    public AiController(AiService aiService, ResumeRepository resumeRepository, SkillRepository skillRepository) {
        this.aiService = aiService;
        this.resumeRepository = resumeRepository;
        this.skillRepository = skillRepository;
    }

    @PostMapping("/resume-review")
    public ResponseEntity<?> reviewResume(@AuthenticationPrincipal UserPrincipal p) {
        Resume resume = resumeRepository.findTopByUserIdOrderByUploadedAtDesc(p.getUserId())
                .orElseThrow(() -> new RuntimeException("No resume found. Please upload a resume first."));

        String text = resume.getExtractedText();
        if (text == null || text.isBlank()) text = "Resume file: " + resume.getFileName();

        AiAnalysis analysis = aiService.reviewResume(p.getUserId(), text);
        return ResponseEntity.ok(analysis);
    }

    @PostMapping("/skill-gap")
    public ResponseEntity<?> skillGap(@AuthenticationPrincipal UserPrincipal p,
                                      @RequestBody Map<String, String> body) {
        String jobDescription = body.get("jobDescription");
        if (jobDescription == null || jobDescription.isBlank()) {
            return ResponseEntity.badRequest().body("Job description is required");
        }

        List<String> userSkills = skillRepository.findByUserId(p.getUserId())
                .stream().map(Skill::getSkillName).collect(Collectors.toList());

        AiAnalysis analysis = aiService.analyzeSkillGap(p.getUserId(), jobDescription, userSkills);
        return ResponseEntity.ok(analysis);
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@AuthenticationPrincipal UserPrincipal p,
                                  @RequestBody Map<String, String> body) {
        String message = body.get("message");
        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body("Message is required");
        }

        List<String> skills = skillRepository.findByUserId(p.getUserId())
                .stream().map(Skill::getSkillName).collect(Collectors.toList());

        String context = "User skills: " + String.join(", ", skills);
        String response = aiService.chat(p.getUserId(), message, context);
        return ResponseEntity.ok(Map.of("response", response));
    }

    @GetMapping("/history")
    public ResponseEntity<List<AiAnalysis>> history(@AuthenticationPrincipal UserPrincipal p) {
        return ResponseEntity.ok(aiService.getHistory(p.getUserId()));
    }
}
