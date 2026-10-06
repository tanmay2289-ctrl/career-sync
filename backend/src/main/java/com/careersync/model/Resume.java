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
@Document(collection = "resumes")
public class Resume {
    @Id
    private String id;
    private String userId;
    private String fileName;
    private String fileUrl;
    private String extractedText;
    private List<String> extractedSkills;
    private String extractedEducation;
    private String extractedExperience;
    private LocalDateTime uploadedAt;
}
