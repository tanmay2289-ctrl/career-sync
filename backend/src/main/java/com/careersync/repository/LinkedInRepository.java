package com.careersync.repository;

import com.careersync.model.LinkedInData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface LinkedInRepository extends MongoRepository<LinkedInData, String> {
    Optional<LinkedInData> findByUserId(String userId);
    void deleteByUserId(String userId);
}
