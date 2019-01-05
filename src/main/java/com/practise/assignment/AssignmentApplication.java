package com.practise.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentApplication {

    private static final Logger log = LoggerFactory.getLogger(AssignmentApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
        log.info("Access in-memory h2 db at http://localhost:8080/h2-console with url='jdbc:h2:mem:testdb;', user='sa', pw=''");
	}

}

