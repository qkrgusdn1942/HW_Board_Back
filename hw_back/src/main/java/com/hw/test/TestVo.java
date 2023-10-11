package com.hw.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_table")
public class TestVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer testid;
	private String 	testname;
	private String 	testcontent;
	
}
