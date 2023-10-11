package com.hw.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Autowired
	private TestRepository testRepository;
	
	public TestVo findByTestId(Integer id) {
		TestVo vo = testRepository.findByTestid(id);
		return vo;
	}
}
