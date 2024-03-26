package com.AchintyaNigam.demo.repository;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.AchintyaNigam.demo.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Profile findByUserId(int userId);

	Optional<Profile> findByUsername(String username);
	

}