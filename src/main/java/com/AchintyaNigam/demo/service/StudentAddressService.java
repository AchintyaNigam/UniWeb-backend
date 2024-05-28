package com.AchintyaNigam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.AchintyaNigam.demo.model.StudentAddress;
import com.AchintyaNigam.demo.repository.StudentAddressRepository;

@Service
public class StudentAddressService {
    @Autowired
    private StudentAddressRepository repository;

	public List<StudentAddress> getAllStudentAddresses() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Cacheable(cacheNames="studentAddress", key = "#userId")
	public StudentAddress getStudentAddress(int userId) {
		// TODO Auto-generated method stub
		return repository.findByUserId(userId);
	}

	public StudentAddress createStudentAddress(StudentAddress studentAddress) {
		// TODO Auto-generated method stub
		return repository.save(studentAddress);
	}

	@CachePut(cacheNames="studentAddress", key = "#userId")
	public StudentAddress updateStudentAddress(int userId, StudentAddress studentAddress) {
		// TODO Auto-generated method stub
		StudentAddress existingStudentProfile = repository.findById(userId).orElse(null);
        if (existingStudentProfile != null) {
            // Update the existing student profile with the new data
            existingStudentProfile.setStreet(studentAddress.getStreet());
            existingStudentProfile.setCity(studentAddress.getCity());
            existingStudentProfile.setZipCode(studentAddress.getZipCode());

            // Save and return the updated student profile
            return repository.save(existingStudentProfile);
        }
		return null;
	}

	@CacheEvict(cacheNames="studentAddress", key = "#userId")
	public void deleteStudentAddress(int userId) {
		repository.deleteById(userId);
	}
    
}