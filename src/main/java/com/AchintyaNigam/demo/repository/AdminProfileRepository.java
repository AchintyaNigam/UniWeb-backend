package com.AchintyaNigam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AchintyaNigam.demo.model.AdminProfile;

@Repository
public interface AdminProfileRepository extends JpaRepository<AdminProfile, Integer> {

	AdminProfile findByUserId(int userId);
	
	

}