package com.careersync.service;

import com.careersync.model.LinkedInData;
import com.careersync.model.Resume;
import com.careersync.model.Skill;
import com.careersync.model.User;
import com.careersync.repository.LinkedInRepository;
import com.careersync.repository.ResumeRepository;
import com.careersync.repository.SkillRepository;
import com.careersync.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkedInService {

    private final LinkedInRepository linkedInRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final ResumeRepository resumeRepository;
    private final AiService aiService;

    public LinkedInService(LinkedInRepository linkedInRepository,
                           UserRepository userRepository,
                           SkillRepository skillRepository,
                           ResumeRepository resumeRepository,
                           AiService aiService) {
        this.linkedInRepository = linkedInRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.resumeRepository = resumeRepository;
        this.aiService = aiService;
    }

    public Optional<LinkedInData> getByUserId(String userId) {
        Optional<LinkedInData> opt = linkedInRepository.findByUserId(userId);
        if (opt.isPresent()) {
            return opt;
        }

        // Initialize from user profile if available
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent() && userOpt.get().getLinkedinUrl() != null && !userOpt.get().getLinkedinUrl().isBlank()) {
            User user = userOpt.get();
            LinkedInData data = new LinkedInData();
            data.setUserId(userId);
            data.setProfileUrl(user.getLinkedinUrl());
            data.setVanityName(extractVanityName(user.getLinkedinUrl()));
            data.setHeadline(user.getTargetRole() != null ? user.getTargetRole() : "Software Engineer | Tech Enthusiast");
            data.setLocation(user.getLocation() != null ? user.getLocation() : "India");
            data.setAbout(user.getBio() != null ? user.getBio() : "");
            data.setEducation(user.getEducation() != null ? user.getEducation() : "");
            data.setConnections(0);

            // Populate skills from existing user skills
            List<String> userSkills = skillRepository.findByUserId(userId).stream()
                    .map(Skill::getSkillName)
                    .limit(10)
                    .collect(Collectors.toList());
            data.setTopSkills(userSkills);

            data.setProfileStrength(calculateProfileStrength(data));
            data.setLastUpdated(LocalDateTime.now());
            return Optional.of(linkedInRepository.save(data));
        }

        return Optional.empty();
    }

    public LinkedInData saveOrUpdate(String userId, LinkedInData input) {
        LinkedInData data = linkedInRepository.findByUserId(userId).orElse(new LinkedInData());
        data.setUserId(userId);

        if (input.getProfileUrl() != null && !input.getProfileUrl().isBlank()) {
            data.setProfileUrl(input.getProfileUrl().trim());
            data.setVanityName(extractVanityName(input.getProfileUrl().trim()));
            // Also sync back to User model
            userRepository.findById(userId).ifPresent(u -> {
                u.setLinkedinUrl(data.getProfileUrl());
                userRepository.save(u);
            });
        }
        if (input.getHeadline() != null) data.setHeadline(input.getHeadline());
        if (input.getCurrentPosition() != null) data.setCurrentPosition(input.getCurrentPosition());
        if (input.getCompany() != null) data.setCompany(input.getCompany());
        if (input.getLocation() != null) data.setLocation(input.getLocation());
        if (input.getAbout() != null) data.setAbout(input.getAbout());
        if (input.getConnections() != null) data.setConnections(input.getConnections());
        if (input.getEducation() != null) data.setEducation(input.getEducation());
        if (input.getTopSkills() != null) data.setTopSkills(input.getTopSkills());
        if (input.getExperiences() != null) data.setExperiences(input.getExperiences());

        data.setProfileStrength(calculateProfileStrength(data));
        data.setLastUpdated(LocalDateTime.now());
        return linkedInRepository.save(data);
    }

    public LinkedInData optimizeWithAi(String userId) {
        LinkedInData data = getByUserId(userId).orElseGet(() -> {
            LinkedInData d = new LinkedInData();
            d.setUserId(userId);
            d.setHeadline("Software Engineer | Full Stack Developer");
            return d;
        });

        // Gather context from skills and resume
        List<String> skills = skillRepository.findByUserId(userId).stream()
                .map(Skill::getSkillName).collect(Collectors.toList());
        Optional<Resume> resumeOpt = resumeRepository.findTopByUserIdOrderByUploadedAtDesc(userId);
        String resumeSummary = resumeOpt.map(r -> r.getExtractedText() != null ? r.getExtractedText().substring(0, Math.min(600, r.getExtractedText().length())) : "").orElse("");

        String prompt = String.format(
            "You are a LinkedIn optimization expert and senior technical recruiter. Optimize this candidate's LinkedIn presence for maximum recruiter outreach and ATS visibility.\n\n" +
            "Current Profile Details:\n" +
            "- Current Headline: %s\n" +
            "- Current About: %s\n" +
            "- Key Skills: %s\n" +
            "- Experience/Resume Context: %s\n\n" +
            "CRITICAL INSTRUCTIONS:\n" +
            "- Format ALL list items using circle bullet points ('- ').\n" +
            "- Do NOT use numbered lists anywhere.\n\n" +
            "Provide:\n" +
            "## 3 High-Impact Headline Options\n" +
            "- [Headline Option 1: Role + Core Stack + Impact]\n" +
            "- [Headline Option 2: Target Specialization + Keywords]\n" +
            "- [Headline Option 3: Problem Solver & Value Proposition]\n\n" +
            "## Optimized 'About' Summary\n" +
            "- [Engaging hook line introducing candidate background]\n" +
            "- [Core technical proficiencies and notable achievements]\n" +
            "- [What drives them and call to action to connect]\n\n" +
            "## Essential Recruiter Search Keywords\n" +
            "- [Keyword list to embed in Skills & Experience sections]\n\n" +
            "## Profile Visibility Action Steps\n" +
            "- [Action step 1 to boost creator/recruiter mode]\n" +
            "- [Action step 2 to grow technical network]",
            data.getHeadline() != null ? data.getHeadline() : "Software Engineer",
            data.getAbout() != null ? data.getAbout() : "Passionate developer eager to build scalable web applications.",
            String.join(", ", skills),
            resumeSummary
        );

        String optimizationResult = aiService.callGemini(prompt);
        data.setAiOptimization(optimizationResult);
        data.setLastUpdated(LocalDateTime.now());
        return linkedInRepository.save(data);
    }

    private String extractVanityName(String url) {
        if (url == null || url.isBlank()) return "profile";
        String clean = url.trim().replaceAll("/+$", "");
        if (clean.contains("/in/")) {
            return clean.substring(clean.lastIndexOf("/in/") + 4);
        }
        if (clean.contains("/")) {
            return clean.substring(clean.lastIndexOf('/') + 1);
        }
        return clean;
    }

    private int calculateProfileStrength(LinkedInData data) {
        int score = 20; // baseline
        if (data.getProfileUrl() != null && !data.getProfileUrl().isBlank()) score += 15;
        if (data.getHeadline() != null && data.getHeadline().length() > 15) score += 15;
        if (data.getAbout() != null && data.getAbout().length() > 30) score += 15;
        if (data.getTopSkills() != null && !data.getTopSkills().isEmpty()) score += 15;
        if (data.getExperiences() != null && !data.getExperiences().isEmpty()) score += 10;
        if (data.getEducation() != null && !data.getEducation().isBlank()) score += 10;
        return Math.min(100, score);
    }
}
