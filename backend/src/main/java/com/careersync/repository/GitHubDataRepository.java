package com.careersync.repository;

import com.careersync.model.GitHubData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface GitHubDataRepository extends MongoRepository<GitHubData, String> {
    Optional<GitHubData> findByUserId(String userId);
}
