package com.AchintyaNigam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AchintyaNigam.demo.model.Profile;
import com.AchintyaNigam.demo.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class MainProfileController {
    @Autowired
    private ProfileService service;
    
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('admin')")
    public List<Profile> getAllProfiles()
    {
    	return service.getAllProfiles();
    }
    
    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Profile> getProfile(@PathVariable("userId") int userId)
    {
    	Profile profile = service.getProfile(userId);
        
        if (profile != null) {
            return ResponseEntity.ok(profile); // Return 200 OK with the user profile
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
        }
    }
    
    @PostMapping("/post")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = service.createProfile(profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }
    
    @PutMapping("update/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Profile> updateProfile(@PathVariable int userId, @RequestBody Profile profile) {
        Profile updatedProfile = service.updateProfile(userId, profile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteProfile(@PathVariable int userId) {
        service.deleteProfile(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
