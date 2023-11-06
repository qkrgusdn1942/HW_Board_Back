package com.hw.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.common.TokenDto;

@RestController
@RequestMapping(value="/user/*")
public class LoginCtrl {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/join")
	public ResponseEntity<?> join (@RequestBody UserEntity userEntity) {
		ResponseEntity responseEntity = null;
		
		try {
			
			log.info("loginId :: " + userEntity.getLoginId());
			log.info("password :: " + userEntity.getPassword());
			log.info("username :: " + userEntity.getUserName());
			
			TokenDto tokenDto = userService.generateToken(userEntity.getLoginId());
			
		}catch (Exception e) {
			
		}
		
		return null;
	}


}
