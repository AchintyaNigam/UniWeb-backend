package com.AchintyaNigam.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AchintyaNigam.demo.model.StudentMarks;

@Repository
public interface StudentMarksRepository extends JpaRepository<StudentMarks, Integer> {
	List<StudentMarks> findByUserId(int userId);
}
