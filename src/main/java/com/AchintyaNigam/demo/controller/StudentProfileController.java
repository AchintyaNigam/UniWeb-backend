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
import org.springframework.security.core.Authentication;

import com.AchintyaNigam.demo.model.StudentProfile;
import com.AchintyaNigam.demo.service.StudentProfileService;

@RestController
@RequestMapping("/api/student/profile")
public class StudentProfileController {
    @Autowired
    private StudentProfileService service;
    
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public List<StudentProfile> getAllStudentProfiles()
    {
    	return service.getAllStudentProfiles();
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher') or hasAuthority('student')")
    public ResponseEntity<StudentProfile> getStudentProfile(@PathVariable("userId") int userId, Authentication authentication) {
        // Extract user details from the authentication object
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));
        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("teacher"));
        if (isAdmin || isTeacher|| userId == currentUserId) {
            StudentProfile profile = service.getStudentProfile(userId);

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
    public ResponseEntity<StudentProfile> createStudentProfile(@RequestBody StudentProfile studentProfile) {
        StudentProfile createdStudentProfile = service.createStudentProfile(studentProfile);
        return new ResponseEntity<>(createdStudentProfile, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student')")
    public ResponseEntity<StudentProfile> updateStudentProfile(@PathVariable int userId, @RequestBody StudentProfile studentProfile, Authentication authentication) {
        // Extract user details from the authentication object
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));

        if (isAdmin || userId == currentUserId) {
            StudentProfile updatedStudentProfile = service.updateStudentProfile(userId, studentProfile);
            return ResponseEntity.ok(updatedStudentProfile); // Return 200 OK with the updated profile
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
        }
    }


    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteStudentProfile(@PathVariable int userId) {
        service.deleteStudentProfile(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}