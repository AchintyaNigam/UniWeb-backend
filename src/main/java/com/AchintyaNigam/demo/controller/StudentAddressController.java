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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import com.AchintyaNigam.demo.model.StudentAddress;
import com.AchintyaNigam.demo.service.StudentAddressService;

@RestController
@RequestMapping("/api/student/address")
public class StudentAddressController {
    @Autowired
    private StudentAddressService service;
    
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('admin')")
    public List<StudentAddress> getAllStudentAddresses()
    {
    	return service.getAllStudentAddresses();
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student') or hasAuthority('teacher')")
    public ResponseEntity<StudentAddress> getStudentAddress(@PathVariable("userId") int userId, Authentication authentication) {
        // Extract user details from the authentication object
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));
        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("teacher"));
        if (isAdmin || isTeacher ||userId == currentUserId) {
            StudentAddress address = service.getStudentAddress(userId);

            if (address != null) {
                return ResponseEntity.ok(address); // Return 200 OK with the user profile
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
        }
    }

    
    @PostMapping("/post")
    public ResponseEntity<StudentAddress> createStudentAddress(@RequestBody StudentAddress studentAddress) {
        StudentAddress createdStudentAddress = service.createStudentAddress(studentAddress);
        return new ResponseEntity<>(createdStudentAddress, HttpStatus.CREATED);
    }

    @PutMapping("update/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student')")
    public ResponseEntity<StudentAddress> updateStudentAddress(@PathVariable int userId, @RequestBody StudentAddress studentAddress, Authentication authentication) {
        // Extract user details from the authentication object
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));

        if (isAdmin || userId == currentUserId) {
            StudentAddress updatedStudentAddress = service.updateStudentAddress(userId, studentAddress);
            return ResponseEntity.ok(updatedStudentAddress); // Return 200 OK with the updated address
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
        }
    }


    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student')")
    public ResponseEntity<Void> deleteStudentAddress(@PathVariable int userId, Authentication authentication) {
        // Extract user details from the authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details

        // Check if the current user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));

        if (isAdmin || userId == currentUserId) {
            service.deleteStudentAddress(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content if delete is successful
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
        }
    }

}