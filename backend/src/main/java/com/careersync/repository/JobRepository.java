package com.careersync.repository;

import com.careersync.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByUserId(String userId);
    List<Job> findByUserIdAndStatus(String userId, String status);
    long countByUserIdAndStatus(String userId, String status);
}
