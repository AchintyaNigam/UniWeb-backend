package com.AchintyaNigam.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AchintyaNigam.demo.config.AuthRequest;
import com.AchintyaNigam.demo.model.Profile;
import com.AchintyaNigam.demo.repository.ProfileRepository;
import com.AchintyaNigam.demo.service.JWTService;
import com.AchintyaNigam.demo.service.UserInfoUserDetailsService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private ProfileRepository profileRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/post")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
	    if (authentication.isAuthenticated()) {
	        // Fetch user profile from the repository
	        Optional<Profile> optionalProfile = profileRepo.findByUsername(authRequest.getUsername());
	        if (optionalProfile.isPresent()) {
	            Profile profile = optionalProfile.get();
	            
	            // Get user role and ID from the profile
	            String role = profile.getRole();
	            int userId = profile.getUserId();

	            // Generate JWT token including user role and ID in the payload
	            String token = jwtService.generateToken(authRequest.getUsername());
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("token", token);
	            response.put("role", role);
	            response.put("userId", userId);
	            return ResponseEntity.ok().body(response);
	        } else {
	            throw new UsernameNotFoundException("User not found");
	        }
	    } else {
	        throw new UsernameNotFoundException("Invalid user request!");
	    }
	}



}
