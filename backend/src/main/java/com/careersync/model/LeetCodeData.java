package com.careersync.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "leetcode_data")
public class LeetCodeData {
    @Id
    private String id;
    private String userId;
    private String username;
    private Integer totalSolved;
    private Integer easy;
    private Integer medium;
    private Integer hard;
    private Integer ranking;
    private Integer totalEasy;
    private Integer totalMedium;
    private Integer totalHard;
    private Integer acceptanceRate;
    private LocalDateTime lastUpdated;
}
