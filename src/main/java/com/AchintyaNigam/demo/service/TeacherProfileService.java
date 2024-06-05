package com.AchintyaNigam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.AchintyaNigam.demo.model.TeacherProfile;
import com.AchintyaNigam.demo.repository.TeacherProfileRepository;

@Service
public class TeacherProfileService {
    @Autowired
    private TeacherProfileRepository repository;

	public List<TeacherProfile> getAllTeacherProfiles() {
		return repository.findAll();
	}

	public TeacherProfile getTeacherProfile(int userId) {
		return repository.findByUserId(userId);
	}

	public TeacherProfile createTeacherProfile(TeacherProfile teacherProfile) {
		return repository.save(teacherProfile);
	}

	public TeacherProfile updateTeacherProfile(int userId, TeacherProfile teacherProfile) {
		TeacherProfile existingTeacherProfile = repository.findById(userId).orElse(null);
        if (existingTeacherProfile != null) {
            // Update the existing student profile with the new data
            existingTeacherProfile.setDepartment(teacherProfile.getDepartment());

            // Save and return the updated student profile
            return repository.save(existingTeacherProfile);
        }
		return null;
	}

	public void deleteTeacherProfile(int userId) {
		repository.deleteById(userId);
	}
    
}