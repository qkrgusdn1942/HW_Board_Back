package com.hw.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hw.common.TokenDto;
import com.hw.common.TokenProvide;
import com.hw.exception.DuplicatedUsernameException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	// 암호화 위한 엔코더
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	UserRepository userRepository;

	private final TokenProvide tokenProvide;
	
	 /**
     * 회원가입
     * @param userEntity
     * @return boolean
     */
	public boolean join (UserEntity userEntity) {
		// 기존 회원 체크
		if(userRepository.findByLoginId(userEntity.getLoginId()).isPresent()) {
			log.info("이미 가입된 회원");
			throw new DuplicatedUsernameException("이미 가입된 회원입니다.");
		} 
		
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		userEntity.setRole("ROLE_USER");
		userRepository.save(userEntity);
		
		return true;
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
