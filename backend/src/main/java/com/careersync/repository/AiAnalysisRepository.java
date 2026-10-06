package com.careersync.repository;

import com.careersync.model.AiAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AiAnalysisRepository extends MongoRepository<AiAnalysis, String> {
    List<AiAnalysis> findByUserIdOrderByCreatedAtDesc(String userId);
    List<AiAnalysis> findByUserIdAndAnalysisTypeOrderByCreatedAtDesc(String userId, String analysisType);
}
