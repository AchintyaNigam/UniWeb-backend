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
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Cacheable(cacheNames = "teacherProfile", key = "#userId")
	public TeacherProfile getTeacherProfile(int userId) {
		// TODO Auto-generated method stub
		return repository.findByUserId(userId);
	}

	public TeacherProfile createTeacherProfile(TeacherProfile teacherProfile) {
		// TODO Auto-generated method stub
		return repository.save(teacherProfile);
	}

	@CachePut(cacheNames = "teacherProfile", key = "#userId")
	public TeacherProfile updateTeacherProfile(int userId, TeacherProfile teacherProfile) {
		// TODO Auto-generated method stub
		TeacherProfile existingTeacherProfile = repository.findById(userId).orElse(null);
        if (existingTeacherProfile != null) {
            // Update the existing student profile with the new data
            existingTeacherProfile.setDepartment(teacherProfile.getDepartment());

            // Save and return the updated student profile
            return repository.save(existingTeacherProfile);
        }
		return null;
	}

	@CacheEvict(cacheNames = "teacherProfile", key = "#userId")
	public void deleteTeacherProfile(int userId) {
		repository.deleteById(userId);
	}
    
}