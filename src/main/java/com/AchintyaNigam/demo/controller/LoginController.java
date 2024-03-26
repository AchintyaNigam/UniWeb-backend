package com.AchintyaNigam.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@GetMapping("/get")
    public ResponseEntity<String> login()
    {
    	return new ResponseEntity<>("Coming Soon", HttpStatus.OK);
    }

}
