package com.careersync.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    private String userId;
    private String company;
    private String role;
    private String location;
    private String jobUrl;
    private LocalDate applicationDate;
    private LocalDate deadline;
    private String status; // Saved, Applied, Shortlisted, Interview, Selected, Rejected
    private String notes;
    private String type; // Job, Internship
}
