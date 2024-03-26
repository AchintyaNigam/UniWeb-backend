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

import com.AchintyaNigam.demo.model.StudentMarks;
import com.AchintyaNigam.demo.service.StudentMarksService;

@RestController
@RequestMapping("/api/student/marks")
public class StudentMarksController {
    @Autowired
    private StudentMarksService service;
    
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public List<StudentMarks> getAllStudentMarks()
    {
    	return service.getAllStudentMarks();
    }
    
    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('student') or hasAuthority('teacher')")
    public ResponseEntity<StudentMarks> getStudentMarks(@PathVariable("userId") int userId)
    {

        StudentMarks marks = service.getStudentMarks(userId);

       
        if (marks != null) {
            return ResponseEntity.ok(marks); // Return 200 OK with the user profile
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user not found
        }
    }
    
    @PostMapping("/post")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public ResponseEntity<StudentMarks> createStudentMarks(@RequestBody StudentMarks studentMarks) {
        StudentMarks createdStudentMarks = service.createStudentMarks(studentMarks);
        return new ResponseEntity<>(createdStudentMarks, HttpStatus.CREATED);
    }
    
    @PutMapping("update/{id}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public ResponseEntity<StudentMarks> updateStudentMarks(@PathVariable int id, @RequestBody StudentMarks studentMarks) {
        StudentMarks updatedStudentMarks = service.updateStudentMarks(id, studentMarks);
        return new ResponseEntity<>(updatedStudentMarks, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public ResponseEntity<Void> deleteStudentMarks(@PathVariable int id) {
        service.deleteStudentMarks(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}