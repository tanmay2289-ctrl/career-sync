package com.careersync.repository;

import com.careersync.model.LeetCodeData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface LeetCodeDataRepository extends MongoRepository<LeetCodeData, String> {
    Optional<LeetCodeData> findByUserId(String userId);
}
