package com.careersync.repository;

import com.careersync.model.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ResumeRepository extends MongoRepository<Resume, String> {
    Optional<Resume> findTopByUserIdOrderByUploadedAtDesc(String userId);
    void deleteByUserId(String userId);
}
