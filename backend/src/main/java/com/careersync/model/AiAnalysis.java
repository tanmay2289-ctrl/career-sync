package com.careersync.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ai_analysis")
public class AiAnalysis {
    @Id
    private String id;
    private String userId;
    private String analysisType; // RESUME_REVIEW, SKILL_GAP, CAREER_ADVICE, CHAT
    private String inputData;
    private String result;
    private List<String> recommendations;

    @CreatedDate
    private LocalDateTime createdAt;
}
