package com.AchintyaNigam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AchintyaNigam.demo.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Profile findByUserId(int userId);

}