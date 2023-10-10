package com.hw.test;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestVo, Integer>{

	public Optional<TestVo> findById(Integer id);
}
