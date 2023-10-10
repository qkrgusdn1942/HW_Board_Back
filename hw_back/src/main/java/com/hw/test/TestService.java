package com.hw.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Autowired
	private TestRepository testRepository;
	
	public Optional<TestVo> findById(Integer id) {
		Optional<TestVo> vo = testRepository.findById(id);
		return vo;
	}
}
