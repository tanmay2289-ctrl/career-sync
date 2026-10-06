package com.careersync.repository;

import com.careersync.model.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GoalRepository extends MongoRepository<Goal, String> {
    List<Goal> findByUserId(String userId);
    List<Goal> findByUserIdAndStatus(String userId, String status);
}
