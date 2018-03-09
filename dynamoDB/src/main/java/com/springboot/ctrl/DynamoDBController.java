package com.springboot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Student;
import com.springboot.repository.DynamoDBRepository;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDBController {

	@Autowired
	private DynamoDBRepository repository;

	@PostMapping
	public String insertIntoDynamoDB(@RequestBody Student student) {
		repository.insertIntoDynamoDB(student);
		return "Successfully inserted into DynamoDB table";
	}

	@GetMapping
	public ResponseEntity<Student> getOneStudentDetails(@RequestParam String studentId, @RequestParam String lastName) {
		Student student = repository.getOneStudentDetails(studentId, lastName);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
}
