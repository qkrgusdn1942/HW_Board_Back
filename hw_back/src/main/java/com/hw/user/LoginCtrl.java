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
import com.hw.dto.UserDto;

@RestController
@RequestMapping(value="/user/*")
public class LoginCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	UserService userService;
	@Autowired
    ResponseService responseService;
	
	/**
     * 회원가입
     * @param userEntity
     * @return response
     */
	@PostMapping(value = "/join")
	public ResponseEntity<?> join (@RequestBody UserDto userDto) {
		ResponseEntity<?> responseEntity = null;
		
		try {
			
			logger.info("------------- 회원가입 -------------");
			
			// 회원가입 
			userService.join(userDto);

		}catch (Exception e) {
			BaseResponse response = responseService.getBaseResponse(false, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		logger.info("------------- 회원가입 -------------");
		return responseEntity;
	}

	/**
     * 로그인
     * @param userEntity
     * @return response
     */
	@PostMapping(value = "/login")
	public ResponseEntity<?> login (@RequestBody UserDto userDto) {
		ResponseEntity<?> responseEntity = null;
		
		try {
			
			logger.info("------------- 로그인 -------------");
			
			String loginId =  userService.login(userDto);
			
			// 토큰 생성 
			TokenDto tokenDto = userService.generateToken(loginId);
			
			// 쿠키 
			ResponseCookie responseCookie = 
					ResponseCookie
						.from(HttpHeaders.SET_COOKIE, tokenDto.getRefreshToken())
						.path("/")
						.maxAge(14 * 24 * 60 * 60)
						.build();
			
			// 응답 객체 
			DataResponse<String> response = responseService.getSingleDataResponse(true, loginId, tokenDto.getAccessToken());
			
			responseEntity = 
					ResponseEntity
						.status(HttpStatus.OK)
						.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
						.body(response);
						
		}catch (Exception e) {
			logger.info("! 오류  :: " + e.getMessage());
			BaseResponse response = responseService.getBaseResponse(false, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		logger.info("------------- 로그인 -------------");
		return responseEntity;
	}

}
