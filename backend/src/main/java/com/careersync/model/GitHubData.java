package com.careersync.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "github_data")
public class GitHubData {
    @Id
    private String id;
    private String userId;
    private String username;
    private Integer repositories;
    private Integer followers;
    private Integer following;
    private Integer contributions;
    private Map<String, Integer> languages;
    private List<RepoInfo> topRepos;
    private String avatarUrl;
    private String bio;
    private LocalDateTime lastUpdated;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepoInfo {
        private String name;
        private String description;
        private String url;
        private String language;
        private Integer stars;
        private Integer forks;
    }
}
