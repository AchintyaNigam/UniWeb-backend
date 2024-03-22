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

import com.AchintyaNigam.demo.model.StudentAddress;
import com.AchintyaNigam.demo.service.StudentAddressService;

@RestController
@RequestMapping("/api/student/address")
public class StudentAddressController {
    @Autowired
    private StudentAddressService service;
    
    @GetMapping("/get")
    public List<StudentAddress> getAllStudentAddresses()
    {
    	return service.getAllStudentAddresses();
    }
    
    @GetMapping("/get/{userId}")
    public ResponseEntity<StudentAddress> getStudentAddress(@PathVariable("userId") int userId)
    {
    	StudentAddress address = service.getStudentAddress(userId);
        
        if (address != null) {
            return ResponseEntity.ok(address); // Return 200 OK with the user profile
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
        }
    }
    
    @PostMapping("/post")
    public ResponseEntity<StudentAddress> createStudentAddress(@RequestBody StudentAddress studentAddress) {
        StudentAddress createdStudentAddress = service.createStudentAddress(studentAddress);
        return new ResponseEntity<>(createdStudentAddress, HttpStatus.CREATED);
    }
    
    @PutMapping("update/{userId}")
    public ResponseEntity<StudentAddress> updateStudentAddress(@PathVariable int userId, @RequestBody StudentAddress studentAddress) {
        StudentAddress updatedStudentAddress = service.updateStudentAddress(userId, studentAddress);
        return new ResponseEntity<>(updatedStudentAddress, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<Void> deleteStudentAddress(@PathVariable int userId) {
        service.deleteStudentAddress(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}