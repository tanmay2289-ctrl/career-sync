package com.careersync.service;

import com.careersync.model.Skill;
import com.careersync.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getSkills(String userId) {
        return skillRepository.findByUserId(userId);
    }

    public Skill addSkill(String userId, Skill skill) {
        skill.setUserId(userId);
        return skillRepository.save(skill);
    }

    public List<Skill> addSkillsBatch(String userId, List<String> skillNames) {
        if (skillNames == null || skillNames.isEmpty()) {
            return getSkills(userId);
        }

        List<Skill> existing = skillRepository.findByUserId(userId);
        Set<String> existingNames = existing.stream()
                .map(s -> s.getSkillName().toLowerCase().trim())
                .collect(Collectors.toSet());

        List<Skill> toSave = new ArrayList<>();
        for (String name : skillNames) {
            if (name != null && !name.isBlank() && !existingNames.contains(name.toLowerCase().trim())) {
                Skill s = new Skill();
                s.setUserId(userId);
                s.setSkillName(name.trim());
                s.setCategory(inferCategory(name.trim()));
                s.setProficiency(75);
                s.setYearsExperience(1.0);
                toSave.add(s);
                existingNames.add(name.toLowerCase().trim());
            }
        }
        if (!toSave.isEmpty()) {
            skillRepository.saveAll(toSave);
        }
        return skillRepository.findByUserId(userId);
    }

    private String inferCategory(String skill) {
        String lower = skill.toLowerCase();
        if (lower.contains("react") || lower.contains("html") || lower.contains("css") || lower.contains("tailwind") || lower.contains("angular") || lower.contains("vue") || lower.contains("frontend")) {
            return "Frontend";
        }
        if (lower.contains("java") || lower.contains("python") || lower.contains("c++") || lower.contains("rust") || lower.contains("go") || lower.contains("typescript") || lower.contains("javascript")) {
            return "Programming Language";
        }
        if (lower.contains("spring") || lower.contains("node") || lower.contains("express") || lower.contains("django") || lower.contains("fastapi") || lower.contains("backend") || lower.contains("api")) {
            return "Backend";
        }
        if (lower.contains("mongo") || lower.contains("sql") || lower.contains("postgres") || lower.contains("redis") || lower.contains("database")) {
            return "Database";
        }
        if (lower.contains("docker") || lower.contains("kubernetes") || lower.contains("aws") || lower.contains("azure") || lower.contains("gcp") || lower.contains("jenkins") || lower.contains("ci/cd")) {
            return "DevOps";
        }
        if (lower.contains("machine learning") || lower.contains("deep learning") || lower.contains("tensorflow") || lower.contains("pytorch") || lower.contains("ai")) {
            return "AI/ML";
        }
        if (lower.contains("dsa") || lower.contains("algorithms") || lower.contains("data structures") || lower.contains("system design")) {
            return "DSA";
        }
        return "Tools";
    }

    public Skill updateSkill(String userId, String skillId, Skill updatedSkill) {
        return skillRepository.findById(skillId).map(skill -> {
            if (!skill.getUserId().equals(userId)) throw new RuntimeException("Unauthorized");
            if (updatedSkill.getSkillName() != null) skill.setSkillName(updatedSkill.getSkillName());
            if (updatedSkill.getCategory() != null) skill.setCategory(updatedSkill.getCategory());
            if (updatedSkill.getProficiency() != null) skill.setProficiency(updatedSkill.getProficiency());
            if (updatedSkill.getYearsExperience() != null) skill.setYearsExperience(updatedSkill.getYearsExperience());
            return skillRepository.save(skill);
        }).orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public void deleteSkill(String userId, String skillId) {
        skillRepository.findById(skillId).ifPresent(skill -> {
            if (!skill.getUserId().equals(userId)) throw new RuntimeException("Unauthorized");
            skillRepository.delete(skill);
        });
    }
}
