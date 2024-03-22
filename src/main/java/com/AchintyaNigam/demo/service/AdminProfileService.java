package com.AchintyaNigam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.AchintyaNigam.demo.model.AdminProfile;
import com.AchintyaNigam.demo.repository.AdminProfileRepository;

@Service
public class AdminProfileService {
    @Autowired
    private AdminProfileRepository repository;

	public List<AdminProfile> getAllAdminProfiles() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public AdminProfile getAdminProfile(int userId) {
		// TODO Auto-generated method stub
		return repository.findByUserId(userId);
	}

	public AdminProfile createAdminProfile(AdminProfile adminProfile) {
		// TODO Auto-generated method stub
		return repository.save(adminProfile);
	}
	
	public AdminProfile updateAdminProfile(int userId, AdminProfile adminProfile) {
		// TODO Auto-generated method stub
		AdminProfile existingAdminProfile = repository.findByUserId(userId);
		if(existingAdminProfile != null)
		{
			existingAdminProfile.setDepartment(adminProfile.getDepartment());
		}
		return null;
	}
    
}