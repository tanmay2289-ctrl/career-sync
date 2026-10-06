package com.careersync.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "linkedin_data")
public class LinkedInData {
    @Id
    private String id;
    private String userId;
    private String profileUrl;
    private String vanityName;
    private String headline;
    private String currentPosition;
    private String company;
    private String location;
    private String about;
    private Integer connections;
    private List<String> topSkills;
    private List<ExperienceItem> experiences;
    private String education;
    private String aiOptimization;
    private Integer profileStrength;
    private LocalDateTime lastUpdated;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExperienceItem {
        private String title;
        private String company;
        private String duration;
        private String description;
    }
}
