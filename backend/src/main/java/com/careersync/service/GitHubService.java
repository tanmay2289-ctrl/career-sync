package com.careersync.service;

import com.careersync.model.GitHubData;
import com.careersync.repository.GitHubDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GitHubService {

    private final GitHubDataRepository gitHubDataRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GitHubService(GitHubDataRepository gitHubDataRepository) {
        this.gitHubDataRepository = gitHubDataRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .defaultHeader("User-Agent", "CareerSync-App")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public GitHubData fetchAndSave(String userId, String username) {
        try {
            // Fetch user profile
            String userJson = webClient.get()
                    .uri("/users/{username}", username)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode userNode = objectMapper.readTree(userJson);

            // Fetch top repos
            String reposJson = webClient.get()
                    .uri("/users/{username}/repos?sort=stars&per_page=6", username)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode reposNode = objectMapper.readTree(reposJson);

            GitHubData data = gitHubDataRepository.findByUserId(userId).orElse(new GitHubData());
            data.setUserId(userId);
            data.setUsername(username);
            data.setRepositories(userNode.get("public_repos").asInt());
            data.setFollowers(userNode.get("followers").asInt());
            data.setFollowing(userNode.get("following").asInt());
            data.setAvatarUrl(userNode.get("avatar_url").asText(""));
            data.setBio(userNode.has("bio") && !userNode.get("bio").isNull() ? userNode.get("bio").asText("") : "");

            // Parse top repos
            List<GitHubData.RepoInfo> topRepos = new ArrayList<>();
            Map<String, Integer> languages = new HashMap<>();

            for (JsonNode repo : reposNode) {
                if (!repo.get("fork").asBoolean()) {
                    GitHubData.RepoInfo repoInfo = new GitHubData.RepoInfo();
                    repoInfo.setName(repo.get("name").asText());
                    repoInfo.setDescription(repo.has("description") && !repo.get("description").isNull()
                            ? repo.get("description").asText() : "");
                    repoInfo.setUrl(repo.get("html_url").asText());
                    repoInfo.setStars(repo.get("stargazers_count").asInt());
                    repoInfo.setForks(repo.get("forks_count").asInt());

                    if (repo.has("language") && !repo.get("language").isNull()) {
                        String lang = repo.get("language").asText();
                        repoInfo.setLanguage(lang);
                        languages.merge(lang, 1, Integer::sum);
                    }
                    topRepos.add(repoInfo);
                }
            }

            data.setTopRepos(topRepos);
            data.setLanguages(languages);
            data.setContributions(0); // Contributions require authenticated access
            data.setLastUpdated(LocalDateTime.now());

            return gitHubDataRepository.save(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch GitHub data: " + e.getMessage(), e);
        }
    }

    public Optional<GitHubData> getByUserId(String userId) {
        return gitHubDataRepository.findByUserId(userId);
    }
}
