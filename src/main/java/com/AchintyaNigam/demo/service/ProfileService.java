package com.AchintyaNigam.demo.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.AchintyaNigam.demo.model.Profile;
import com.AchintyaNigam.demo.repository.ProfileRepository;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Cacheable(value = "UniWebCache", keyGenerator = "customKeyGenerator")
	public List<Profile> getAllProfiles() {
		return repository.findAll();
	}

	public Profile getProfile(int userId) {
		System.out.println("DB accessed for user:"+userId);
		return repository.findByUserId(userId);
	}

	public Profile createProfile(Profile profile) {
        String encodedPassword = bCryptPasswordEncoder.encode(profile.getPassword());
        profile.setPassword(encodedPassword);
		return repository.save(profile);
	}

	public Profile updateProfile(int userId, Profile profile) {
		Optional<Profile> existingStudentProfileOptional = repository.findById(userId);
		Profile existingStudentProfile = existingStudentProfileOptional.orElse(null);
        if (existingStudentProfile != null) {
            // Update the existing profile with the new data
            existingStudentProfile.setRole(profile.getRole());
            existingStudentProfile.setUsername(profile.getUsername());
            existingStudentProfile.setPassword(profile.getPassword());
            existingStudentProfile.setFullName(profile.getFullName());
            existingStudentProfile.setEmail(profile.getEmail());
            existingStudentProfile.setBirthdate(profile.getBirthdate());

            // Save and return the updated profile
            return repository.save(existingStudentProfile);
        }
		return null;
	}

	public void deleteProfile(int userId) {
		 repository.deleteById(userId);
		
	}
}