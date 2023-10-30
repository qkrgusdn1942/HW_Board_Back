package com.hw.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCtrl {
	
	@PostMapping(value = "/loginProcess")
	public String login () {
		System.out.println("로그인실행");
		return "login";
	}

}
