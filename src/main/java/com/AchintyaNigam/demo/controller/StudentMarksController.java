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
    public ResponseEntity<List<StudentMarks>> getStudentMarks(@PathVariable("userId") int userId, Authentication authentication)
    {
        Integer currentUserId = (Integer) authentication.getDetails(); // Retrieve userId from authentication details
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));
        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("teacher"));

        if(isAdmin || isTeacher || userId==currentUserId)
        {
            List<StudentMarks> marksList = service.getStudentMarks(userId);


            if (!marksList.isEmpty()) {
                return ResponseEntity.ok(marksList); // Return 200 OK with the list of marks
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if no marks found
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 Forbidden if not authorized
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

    	public ResponseEntity<Void> updateStudentMarks(@RequestBody List<StudentMarks> studentMarks) {
    	    service.updateStudentMarksBatch(studentMarks); // Implement service method for batch update
    	    return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
    public ResponseEntity<Void> deleteStudentMarks(@PathVariable int id) {
        service.deleteStudentMarks(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}