package com.AchintyaNigam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AchintyaNigam.demo.model.StudentAddress;

@Repository
public interface StudentAddressRepository extends JpaRepository<StudentAddress, Integer> {

	StudentAddress findByUserId(int userId);
  
}
