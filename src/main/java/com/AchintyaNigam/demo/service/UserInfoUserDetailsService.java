package com.AchintyaNigam.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.AchintyaNigam.demo.config.UserInfoUserDetails;
import com.AchintyaNigam.demo.model.Profile;
import com.AchintyaNigam.demo.repository.ProfileRepository;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private ProfileRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Profile> profile = repo.findByUsername(username);
		
		return profile.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

	}

}
