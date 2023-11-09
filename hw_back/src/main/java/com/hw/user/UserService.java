package com.hw.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hw.common.TokenDto;
import com.hw.common.TokenProvide;
import com.hw.dto.UserDto;
import com.hw.exception.DuplicatedUsernameException;
import com.hw.exception.LoginFailException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	// 암호화 위한 엔코더
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private final UserRepository userRepository;
	private final TokenProvide tokenProvide;
	
	 /**
     * 회원가입
     * @param userEntity
     * @return boolean
     */
	@Transactional
	public boolean join (UserDto userDto) {
		// 기존 회원 체크
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setRole("ROLE_USER");
		
		if(userRepository.findByLoginId(userDto.getLoginId()).isPresent()) {
			throw new DuplicatedUsernameException("이미 가입된 회원입니다.");
		} 
		
		UserEntity userEntity = UserEntity.toUserEntity(userDto);
		userRepository.save(userEntity);
		return true;
	}
	
	 /**
     * 로그인
     * @param userEntity
     * @return String
     */
	public String login (UserDto userDto) {
		
		UserEntity userEntity = userRepository.findByLoginId(userDto.getLoginId()).orElseThrow(() -> new LoginFailException("회원 정보가 없습니다."));

		if (!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
			throw new LoginFailException("비밀번호가 불일치");
		}
		return userDto.getLoginId();
	}

	/**
     * 토큰 발행
     * @param loginId
     * @return tokenDto
	 * @throws Exception 
     */
	public TokenDto generateToken(String loginId) throws Exception {
		UserEntity userEntity = userRepository.findByLoginId(loginId).orElseThrow(() -> new Exception("토큰 발행 불가 :: 아이디 없음"));
			
		TokenDto tokenDto = new TokenDto();
		tokenDto.setAccessToken("Bearer" + tokenProvide.createAcessToken(userEntity.getLoginId(), userEntity.getRole()));
		tokenDto.setRefreshToken("Bearer" + tokenProvide.createRefreshToken(userEntity.getLoginId(), loginId));
		
		return tokenDto;
	}
}
