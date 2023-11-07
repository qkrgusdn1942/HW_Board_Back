package com.hw.common;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends GenericFilterBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private TokenProvide tokenProvide;
	
	public JwtFilter(TokenProvide tokenProvide) {
	        this.tokenProvide = tokenProvide;
	}

	 /**
     * jwt 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
     */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("---------- DO FILTER 실행 -------------");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();
        
        if(requestURI.contains("/main,/user/*")) {
        	chain.doFilter(request, response);
            return;
        }
		
        Cookie[] cookies = httpServletRequest.getCookies();
        String refreshToken = null;
        String useToken = null;
        
        for (Cookie cookie : cookies) {
        	if (cookie.getName().equals(HttpHeaders.SET_COOKIE)) {
        		refreshToken = cookie.getValue();
        	}
        }
        
        if (requestURI.equals("/token/getAccessToken")) {
        	useToken = tokenProvide.resolveToken(refreshToken);
        	try {
        		if (StringUtils.hasText(useToken) && tokenProvide.validateToken(useToken)) {
        			Authentication authentication = tokenProvide.getAuthentication(useToken);
        			return;
        		}
        	} catch (Exception e) {
        		log.error(e.getMessage(), e);
        	}
        } else {
        	useToken = tokenProvide.resolveToken(httpServletRequest.getHeader("Authorization"));
        }
        
        try {
        	
        	if (StringUtils.hasText(useToken) && tokenProvide.validateToken(useToken)) {
        		Authentication authentication = tokenProvide.getAuthentication(useToken);
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        		log.debug("Security Contextx에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        	} else {
        		log.debug("JWT 유효한 토큰 없음 , uri:{}", requestURI);
        	}
        	chain.doFilter(request, response);
        	// 잘못된 서명인 토큰일 경우
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED,httpServletResponse, e);

        // 만료된 토큰일 경우
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED,httpServletResponse, e);
        }
        // 지원되지 않는 토큰일 경우
        catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED,httpServletResponse, e);

        // 잘못된 토큰일 경우
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED,httpServletResponse, e);
        }
	}
	
	public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        JSONObject responseJson = new JSONObject();
        try {
            responseJson.put("HttpStatus", HttpStatus.UNAUTHORIZED);
            responseJson.put("message", ex.getMessage());
            responseJson.put("status", false);
            responseJson.put("statusCode", 401);
            responseJson.put("code", "Tk401");
            response.getWriter().print(responseJson);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
