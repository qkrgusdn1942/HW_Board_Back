package com.hw.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private TestService testService;

	@GetMapping(value = "/findAll")
	public List<TestVo> testFindAll () {
		List<TestVo> vo = testService.testFindAll();
		return vo;
	}
	
	@PostMapping(value = "/saveTest")
	public String saveTest (TestVo vo) {
		testService.saveTest(vo);
		return "";
	}
}
