package com.hw.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCtrl {
	
	@PostMapping(value = "/loginProcess")
	public String login (@RequestBody CustomUserEntity customUserEntity) {
		
		System.out.println(customUserEntity.getPassword());
		System.out.println(customUserEntity.getLoginId());
		
		return "login";
	}

}
