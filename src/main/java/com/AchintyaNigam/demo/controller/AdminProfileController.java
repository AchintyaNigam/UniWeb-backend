package com.AchintyaNigam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AchintyaNigam.demo.service.AdminProfileService;
import com.AchintyaNigam.demo.model.AdminProfile;

@RestController
@RequestMapping("/api/admin/profile")
public class AdminProfileController {
    @Autowired
    private AdminProfileService service;
    
    @GetMapping("/get")
    public List<AdminProfile> getAllAdminProfiles()
    {
    	return service.getAllAdminProfiles();
    }
    
    @GetMapping("/get/{userId}")
    public ResponseEntity<AdminProfile> getStudentAddress(@PathVariable("userId") int userId)
    {
    	AdminProfile adminProfile = service.getAdminProfile(userId);
        
        if (adminProfile != null) {
            return ResponseEntity.ok(adminProfile); // Return 200 OK with the user profile
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
        }
    }
    
    @PostMapping("/post")
    public ResponseEntity<AdminProfile> createAdminProfile(@RequestBody AdminProfile adminProfile) {
        AdminProfile createdAdminProfile = service.createAdminProfile(adminProfile);
        return new ResponseEntity<>(createdAdminProfile, HttpStatus.CREATED);
    }
    
    @PutMapping("update/{userId}")
    public ResponseEntity<AdminProfile> updateAdminProfile(@PathVariable int userId, @RequestBody AdminProfile teacherProfile) {
        AdminProfile updatedAdminProfile = service.updateAdminProfile(userId, teacherProfile);
        return new ResponseEntity<>(updatedAdminProfile, HttpStatus.OK);
    }
    

}