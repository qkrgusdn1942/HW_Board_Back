package com.hw.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Autowired
	private TestRepository testRepository;
	
	public List<TestVo> findById(Integer id) {
		List<TestVo> vo = testRepository.findAll();
		return vo;
	}
}
