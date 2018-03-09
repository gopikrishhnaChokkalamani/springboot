package com.springboot.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.springboot.model.Student;

@Repository
public class DynamoDBRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBRepository.class);

	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(Student student) {
		// TODO Auto-generated method stub
		try {
			DynamoDBSaveExpression expr = new DynamoDBSaveExpression();
			Map expected = new HashMap();
			expected.put("studentId", new ExpectedAttributeValue(false));
			expr.setExpected(expected);
			mapper.save(student, expr);
		} catch (ConditionalCheckFailedException e) {
			LOGGER.error("error in insertIntoDynamoDB method(), object already exists in database - " + e);
		}
	}

	public Student getOneStudentDetails(String studentId, String lastName) {
		return mapper.load(Student.class, studentId, lastName);
	}
}
