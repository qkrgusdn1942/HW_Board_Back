package com.hw.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hw.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor{

	@Autowired
	TokenProvide tokenProvide;
	
	@Autowired
	UserService userService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractTokenFromRequest(request);

        if (token != null) {
            String loginId = tokenProvide.getUserLoginId(token);
            Long userId = userService.getUserIdByLoginId(loginId);

            // 사용자 정보(username)을 활용하여 필요한 작업 수행
            request.setAttribute("userId", userId);
        }

        return true; // true를 반환하면 다음 Interceptor 또는 Controller로 진행, false면 중단
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Controller가 실행된 후 호출
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // View가 렌더링된 후 호출
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // 실제로는 헤더나 쿼리 파라미터 등에서 토큰을 추출하는 로직을 구현해야 합니다.
        // 여기서는 간단한 예시로 request.getParameter()를 사용했습니다.
        return request.getParameter("jwtToken");
    }
	
}
