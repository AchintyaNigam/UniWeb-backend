package com.AchintyaNigam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.AchintyaNigam.demo.model.CreateProfileResponse;
import com.AchintyaNigam.demo.model.Profile;
import com.AchintyaNigam.demo.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class MainProfileController {
    @Autowired
    private ProfileService service;
    
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public List<Profile> getAllProfiles()
    {
    	return service.getAllProfiles();
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student') or hasAuthority('teacher')")
    public ResponseEntity<Profile> getProfile(@PathVariable("userId") int userId, Authentication authentication) {
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));

        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("teacher"));

        if (isAdmin || isTeacher || userId == currentUserId) {
            Profile profile = service.getProfile(userId);
            if (isTeacher && userId != currentUserId && profile.getRole().equals("teacher"))
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
            }

            if (profile != null) {
                return ResponseEntity.ok(profile); // Return 200 OK with the user profile
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
        }
    }



    @PostMapping("/post")
    public ResponseEntity<CreateProfileResponse> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = service.createProfile(profile);
        CreateProfileResponse response = new CreateProfileResponse();
        response.setUserId(createdProfile.getUserId()); 
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("update/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student') or hasAuthority('teacher')")
    public ResponseEntity<Profile> updateProfile(@PathVariable int userId, @RequestBody Profile profile, Authentication authentication) {
        // Extract user details from the authentication object
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));

        if (isAdmin || userId == currentUserId) {
            Profile updatedProfile = service.updateProfile(userId, profile);
            return ResponseEntity.ok(updatedProfile); // Return 200 OK with the updated profile
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
        }
    }


    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteProfile(@PathVariable int userId) {
        service.deleteProfile(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
