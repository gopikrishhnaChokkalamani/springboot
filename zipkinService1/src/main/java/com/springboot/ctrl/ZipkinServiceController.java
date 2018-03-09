package com.springboot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.service.ZipkinService;

@RestController
@RequestMapping("/zipkinService")
public class ZipkinServiceController {
	
	@Autowired
	private ZipkinService service;
	
	@GetMapping
	public String getResponse() {
		String response = service.getResponse();
		return response;
	}

}
