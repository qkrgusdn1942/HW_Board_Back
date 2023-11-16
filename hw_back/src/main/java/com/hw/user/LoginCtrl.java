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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
			// 회원가입 
			userService.join(userDto);

		}catch (Exception e) {
			BaseResponse response = responseService.getBaseResponse(false, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return responseEntity;
	}

	/**
     * 로그인
     * @param userEntity
     * @return response
     */
	@PostMapping(value = "/login")
	public ResponseEntity<?> login (@RequestBody UserDto userDto, HttpServletResponse response) {
		ResponseEntity<?> responseEntity = null;
		
		try {
			
			String loginId =  userService.login(userDto);
			
			// 토큰 생성 
			TokenDto tokenDto = userService.generateToken(loginId);
			
			// 액세스 토큰 쿠키
			Cookie accessCookie = new Cookie("AccessToken", tokenDto.getAccessToken());
			accessCookie.setMaxAge(14 * 24 * 60 * 60);
			accessCookie.setPath("/");
			response.addCookie(accessCookie);
			
			// 액세스 토큰 쿠키
			Cookie refreshCookie = new Cookie("RefreshToken", tokenDto.getRefreshToken());
			refreshCookie.setMaxAge(15 * 24 * 60 * 60);
			refreshCookie.setPath("/");
			response.addCookie(refreshCookie);
			
			// 응답 객체 
			DataResponse<String> responseData = responseService.getSingleDataResponse(true, loginId, tokenDto.getAccessToken());
			
			responseEntity = 
					ResponseEntity
						.status(HttpStatus.OK)
						.body(responseData);
						
		}catch (Exception e) {
			logger.info("! 오류  :: " + e.getMessage());
			BaseResponse badResponse = responseService.getBaseResponse(false, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badResponse);
		}
		logger.info("------------- 로그인 -------------");
		return responseEntity;
	}

}
