package com.careersync.service;

import com.careersync.model.User;
import com.careersync.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getProfile(String userId) {
        return userRepository.findById(userId);
    }

    public User updateProfile(String userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            if (updatedUser.getName() != null) user.setName(updatedUser.getName());
            if (updatedUser.getEducation() != null) user.setEducation(updatedUser.getEducation());
            if (updatedUser.getTargetRole() != null) user.setTargetRole(updatedUser.getTargetRole());
            if (updatedUser.getLinkedinUrl() != null) user.setLinkedinUrl(updatedUser.getLinkedinUrl());
            if (updatedUser.getGithubUsername() != null) user.setGithubUsername(updatedUser.getGithubUsername());
            if (updatedUser.getLeetcodeUsername() != null) user.setLeetcodeUsername(updatedUser.getLeetcodeUsername());
            if (updatedUser.getBio() != null) user.setBio(updatedUser.getBio());
            if (updatedUser.getPhone() != null) user.setPhone(updatedUser.getPhone());
            if (updatedUser.getLocation() != null) user.setLocation(updatedUser.getLocation());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
