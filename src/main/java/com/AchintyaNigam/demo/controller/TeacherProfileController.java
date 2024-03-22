package com.AchintyaNigam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AchintyaNigam.demo.model.TeacherProfile;
import com.AchintyaNigam.demo.service.TeacherProfileService;

@RestController
@RequestMapping("/api/teacher/profile")
public class TeacherProfileController {
    @Autowired
    private TeacherProfileService service;
    
    @GetMapping("/get")
    public List<TeacherProfile> getAllTeacherProfiles()
    {
    	return service.getAllTeacherProfiles();
    }
    
    @GetMapping("/get/{userId}")
    public ResponseEntity<TeacherProfile> getTeacherProfile(@PathVariable("userId") int userId)
    {
    	TeacherProfile profile = service.getTeacherProfile(userId);
        
        if (profile != null) {
            return ResponseEntity.ok(profile); // Return 200 OK with the user profile
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
        }
    }
    
    @PostMapping("/post")
    public ResponseEntity<TeacherProfile> createTeacherProfile(@RequestBody TeacherProfile teacherProfile) {
        TeacherProfile createdTeacherProfile = service.createTeacherProfile(teacherProfile);
        return new ResponseEntity<>(createdTeacherProfile, HttpStatus.CREATED);
    }
    
    @PutMapping("update/{userId}")
    public ResponseEntity<TeacherProfile> updateTeacherProfile(@PathVariable int userId, @RequestBody TeacherProfile teacherProfile) {
        TeacherProfile updatedTeacherProfile = service.updateTeacherProfile(userId, teacherProfile);
        return new ResponseEntity<>(updatedTeacherProfile, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<Void> deleteTeacherProfile(@PathVariable int userId) {
        service.deleteTeacherProfile(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}