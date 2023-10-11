package com.hw.test;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private TestService testService;

	@GetMapping(value = "/test")
	public List<TestVo> test (Integer id) {
		List<TestVo> vo = testService.findById(id);
		return vo;
	}
}
