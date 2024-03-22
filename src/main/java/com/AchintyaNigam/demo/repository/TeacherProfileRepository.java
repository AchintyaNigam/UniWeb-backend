package com.AchintyaNigam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AchintyaNigam.demo.model.TeacherProfile;

@Repository
public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Integer> {

	TeacherProfile findByUserId(int userId);
   
}
