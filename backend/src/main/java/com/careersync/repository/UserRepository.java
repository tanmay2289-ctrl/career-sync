package com.careersync.repository;

import com.careersync.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findBySupabaseUid(String supabaseUid);
    Optional<User> findByEmail(String email);
    boolean existsBySupabaseUid(String supabaseUid);
}
