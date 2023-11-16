package com.hw.common;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvide implements InitializingBean {
	
	private final Logger logger = LoggerFactory.getLogger(TokenProvide.class);

    private static final String AUTHORITIES_KEY = "auth";
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final String secret;
    private Key key;
    
    @Autowired
    UserDetailsService userDetailsService;
	
	public TokenProvide (
		@Value("${jwt-secret}") String secret,
		@Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
        @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
	) {
		this.secret = secret;
		this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds;
	}
	
	@Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
	 
	 /**
     * 액세스 토큰 생성 메서드
     * @param loginId 발급받는 유저의 아이디, 유저 권한
     * @param roles 발급받는 유저의 권한
     * @return 발급받은 토큰을 리턴해줌
     */
	 public String createAcessToken(String loginId, String role) {
		 Claims claims = Jwts.claims().setSubject(loginId);
		 claims.put("role", role);
		 
		 Date now = new Date();
		 Date validity = new Date(now.getTime() + this.accessTokenValidityInMilliseconds);
		 
		 return Jwts.builder()
				 .setSubject(loginId)
				 .setClaims(claims)
				 .setIssuedAt(now) // 토큰 발행 시간
				 .setExpiration(validity) // 토큰 만료 시간
				 .signWith(key, SignatureAlgorithm.HS512)
				 .compact();
	 }
	 
	 /**
     * 리프레쉬 토큰 생성 메서드
     * @param loginId 발급받는 유저의 아이디, 유저 권한
     * @param roles 발급받는 유저의 권한
     * @return 발급받은 토큰을 리턴해줌
     */
	 public String createRefreshToken(String loginId, String role) {
		 Claims claims = Jwts.claims().setSubject(loginId);
		 claims.put("role", role);
		 
		 Date now = new Date();
		 Date validity = new Date(now.getTime() + this.refreshTokenValidityInMilliseconds);
		 
		 return Jwts.builder()
				 .setSubject(loginId)
				 .setClaims(claims)
				 .setIssuedAt(now) // 토큰 발행 시간
				 .setExpiration(validity) // 토큰 만료 시간
				 .signWith(key, SignatureAlgorithm.HS512)
				 .compact();
	 }
	 
	 /**
     * 토큰 이용하여 Authentication 객체 반환 메소드 
     * @param Token
     * @return Authentication 객체
     */
	 public Authentication getAuthentication(String token) {
		 UserDetails userDetails = userDetailsService.loadUserByUsername(getUserLoginId(token));
		 return new UsernamePasswordAuthenticationToken(userDetails, "", getUserAuth(token));
	 }
	 
	 /**
     * 토큰 이용하여 사용자 로그인 ID 반환  
     * @param 발급받은 Token
     * @return String
     */
	 public String getUserLoginId (String token) {
		 return Jwts.parserBuilder()
				 .setSigningKey(key)
				 .build()
				 .parseClaimsJws(token)
				 .getBody()
				 .getSubject();
	 }
	 
	 /**
     * 토큰 이용하여 사용자 권한 
     * @param 발급받은 Token
     * @return String
     */
	 public Collection<? extends GrantedAuthority> getUserAuth (String token) {
		 
		 Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(
        		parseClaims(token)
        		.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
		 
		return authorities;
	 }
	 
	 /**
     * Request Header Token 가져오기   
     * @param Token
     * @return String
     */
	 public String resolveToken(String token) {
		// String token = request.getHeader("Authorization");
		// Authorization Header 문자열이고, Bearer 로 시작해야됨 
		 
		if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
			return token.substring(6);
		}
		return null;
	 }
	 
	 /**
     * 토큰을 파싱하고 발생하는 예외를 처리, 문제가 있을경우 false 반환
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } 
        	catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
        	logger.info("잘못된 JWT 서명입니다.");
            throw new JwtException("잘못된 서명");

        } catch (ExpiredJwtException e) {

            logger.info("만료된 JWT 토큰입니다.");
            throw new JwtException("만료된 토큰");

        } catch (UnsupportedJwtException e) {

        	logger.info("지원되지 않는 JWT 토큰입니다.");
        	throw new JwtException("지원되지 않는 토큰");

        } catch (IllegalArgumentException e) {

        	logger.info("JWT 토큰이 잘못되었습니다.");
            throw new JwtException("잘못된 토큰");
        }
     }
    
    /**
     * Request Token 파싱  
     * @param Token
     * @return Claims
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts
            		.parserBuilder()
            		.setSigningKey(key)
            		.build()
            		.parseClaimsJws(accessToken)
            		.getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
	 
}
