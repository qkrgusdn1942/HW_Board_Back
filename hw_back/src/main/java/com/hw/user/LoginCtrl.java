package com.hw.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.common.BaseResponse;
import com.hw.common.DataResponse;
import com.hw.common.ResponseService;
import com.hw.common.TokenDto;

@RestController
@RequestMapping(value="/user/*")
public class LoginCtrl {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	UserService userService;
	@Autowired
    ResponseService responseService;
	
	@PostMapping(value = "/join")
	public ResponseEntity<?> join (@RequestBody UserEntity userEntity) {
		ResponseEntity<?> responseEntity = null;
		
		try {
			
			log.info("loginId :: " + userEntity.getLoginId());
			log.info("password :: " + userEntity.getPassword());
			log.info("username :: " + userEntity.getUserName());
			
			// 회원가입 
			userService.join(userEntity);
			
			// 토큰 생성 
			log.info("토큰생성 !");
			TokenDto tokenDto = userService.generateToken(userEntity.getLoginId());
			log.info("토큰 :: " + tokenDto);
			
			// 쿠키 
			ResponseCookie responseCookie = 
					ResponseCookie
						.from(HttpHeaders.SET_COOKIE, tokenDto.getRefreshToken())
						.path("/")
						.maxAge(14 * 24 * 60 * 60)
						.build();
			log.info(responseCookie.toString());
			
			// 응답 객체 
			DataResponse<String> response = responseService.getSingleDataResponse(true, userEntity.getLoginId(), tokenDto.getAccessToken());
			
			responseEntity = 
					ResponseEntity
						.status(HttpStatus.OK)
						.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
						.body(response);
		}catch (Exception e) {
			BaseResponse response = responseService.getBaseResponse(false, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		return responseEntity;
	}


}
