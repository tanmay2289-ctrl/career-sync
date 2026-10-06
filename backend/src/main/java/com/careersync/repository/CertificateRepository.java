package com.careersync.repository;

import com.careersync.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CertificateRepository extends MongoRepository<Certificate, String> {
    List<Certificate> findByUserId(String userId);
}
