package com.AchintyaNigam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.AchintyaNigam.demo.model.StudentMarks;
import com.AchintyaNigam.demo.repository.StudentMarksRepository;

@Service
public class StudentMarksService {
    @Autowired
    private StudentMarksRepository repository;

	public List<StudentMarks> getAllStudentMarks() {
		return repository.findAll();
	}

	@Cacheable(cacheNames = "studentMarks", key = "#userId")
	public List<StudentMarks> getStudentMarks(int userId) {
		return repository.findByUserId(userId);
	}

	public StudentMarks createStudentMarks(StudentMarks studentMarks) {
		return repository.save(studentMarks);
	}

	@CachePut(cacheNames = "studentMarks", key = "#id")
	public StudentMarks updateStudentMarks(int id, StudentMarks studentMarks) {
		StudentMarks existingProfile = repository.findById(id).orElse(null);
        if (existingProfile != null) {
            // Update the existing profile with the new data
            existingProfile.setId(studentMarks.getId());
            existingProfile.setSubject(studentMarks.getSubject());
            existingProfile.setMarks(studentMarks.getMarks());
         
            // Save and return the updated profile
            return repository.save(existingProfile);
        }
		return null;
	}

	@CacheEvict(cacheNames = "studentMarks", key = "#id")
	public void deleteStudentMarks(int id) {
		repository.deleteById(id);
	}

	public void updateStudentMarksBatch(List<StudentMarks> studentMarks) {
		repository.saveAll(studentMarks);
	}
		
    
}