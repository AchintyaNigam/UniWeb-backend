package com.AchintyaNigam.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@EnableCaching  
@EnableEncryptableProperties
public class UniWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniWebApplication.class, args);
	}

}
