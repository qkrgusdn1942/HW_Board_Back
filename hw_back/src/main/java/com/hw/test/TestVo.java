package com.hw.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="test_table")
@NoArgsConstructor
public class TestVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer test_id;
	private String 	test_name;
	private String 	test_content;
	
}
