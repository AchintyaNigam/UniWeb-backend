package com.AchintyaNigam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.AchintyaNigam.demo.model.StudentProfile;
import com.AchintyaNigam.demo.repository.StudentProfileRepository;

@Service
public class StudentProfileService {
    @Autowired
    private StudentProfileRepository repository;

	public List<StudentProfile> getAllStudentProfiles() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Cacheable(cacheNames = "studentProfile", key="#userId")
	public StudentProfile getStudentProfile(int userId) {
		// TODO Auto-generated method stub
		return repository.findByUserId(userId);
	}

	public StudentProfile createStudentProfile(StudentProfile studentProfile) {
		// TODO Auto-generated method stub
		return repository.save(studentProfile);
	}

	@CachePut(cacheNames = "studentProfile", key="#userId")
	public StudentProfile updateStudentProfile(int userId, StudentProfile studentProfile) {
		// TODO Auto-generated method stub
		StudentProfile existingStudentProfile = repository.findByUserId(userId);
        if (existingStudentProfile != null) {
            // Update the existing student profile with the new data
            existingStudentProfile.setRollNumber(studentProfile.getRollNumber());
            existingStudentProfile.setBranch(studentProfile.getBranch());

            // Save and return the updated student profile
            return repository.save(existingStudentProfile);
        }
		return null;
	}

	@CacheEvict(cacheNames = "studentProfile", key="#userId")
	public void deleteStudentProfile(int userId) {
		// TODO Auto-generated method stub
		repository.deleteById(userId);
		
	}
    
}