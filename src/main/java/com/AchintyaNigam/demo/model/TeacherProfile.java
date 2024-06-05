package com.AchintyaNigam.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "teacher_profile")
public class TeacherProfile implements Serializable {
    
    @Id
    @Column(name = "user_id")
    private int userId;

    private String department;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

    // Constructors, getters, and setters
}