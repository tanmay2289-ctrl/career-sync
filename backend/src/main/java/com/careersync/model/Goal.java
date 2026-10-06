package com.careersync.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "goals")
public class Goal {
    @Id
    private String id;
    private String userId;
    private String title;
    private String category; // DSA, Java, Web Development, Projects, Internship, Placement, Certification
    private LocalDate targetDate;
    private Integer progress; // 0-100
    private String status; // Active, Completed, Paused
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;
}
