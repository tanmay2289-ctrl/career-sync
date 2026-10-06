package com.careersync.service;

import com.careersync.model.LeetCodeData;
import com.careersync.repository.LeetCodeDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LeetCodeService {

    private final LeetCodeDataRepository leetCodeDataRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public LeetCodeService(LeetCodeDataRepository leetCodeDataRepository) {
        this.leetCodeDataRepository = leetCodeDataRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://leetcode-stats-api.herokuapp.com")
                .defaultHeader("User-Agent", "CareerSync-App")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public LeetCodeData fetchAndSave(String userId, String username) {
        try {
            String json = webClient.get()
                    .uri("/{username}", username)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode node = objectMapper.readTree(json);

            LeetCodeData data = leetCodeDataRepository.findByUserId(userId).orElse(new LeetCodeData());
            data.setUserId(userId);
            data.setUsername(username);
            data.setTotalSolved(node.has("totalSolved") ? node.get("totalSolved").asInt() : 0);
            data.setEasy(node.has("easySolved") ? node.get("easySolved").asInt() : 0);
            data.setMedium(node.has("mediumSolved") ? node.get("mediumSolved").asInt() : 0);
            data.setHard(node.has("hardSolved") ? node.get("hardSolved").asInt() : 0);
            data.setRanking(node.has("ranking") ? node.get("ranking").asInt() : 0);
            data.setTotalEasy(node.has("totalEasy") ? node.get("totalEasy").asInt() : 0);
            data.setTotalMedium(node.has("totalMedium") ? node.get("totalMedium").asInt() : 0);
            data.setTotalHard(node.has("totalHard") ? node.get("totalHard").asInt() : 0);
            data.setLastUpdated(LocalDateTime.now());

            return leetCodeDataRepository.save(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch LeetCode data: " + e.getMessage(), e);
        }
    }

    public Optional<LeetCodeData> getByUserId(String userId) {
        return leetCodeDataRepository.findByUserId(userId);
    }
}
