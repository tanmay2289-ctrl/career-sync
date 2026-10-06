package com.careersync.repository;

import com.careersync.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SkillRepository extends MongoRepository<Skill, String> {
    List<Skill> findByUserId(String userId);
    void deleteByIdAndUserId(String id, String userId);
}
