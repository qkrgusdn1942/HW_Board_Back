package com.hw.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private TestService testService;

	@GetMapping(value = "/test")
	public TestVo test (Integer id) {
		System.out.println("실행!!" + id);
		return testService.findByTestId(id);
	}
}
