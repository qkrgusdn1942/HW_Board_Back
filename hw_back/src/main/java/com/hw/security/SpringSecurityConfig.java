package com.hw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.hw.common.JwtAccessDeniedHandler;
import com.hw.common.JwtAuthenticationEntryPoint;
import com.hw.common.JwtSecurityConfig;
import com.hw.common.TokenProvide;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	private final TokenProvide tokenProvide;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    
    public SpringSecurityConfig(
		TokenProvide tokenProvide,
		CorsFilter corsFilter,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler
	
	){
        this.tokenProvide = tokenProvide;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        	.csrf((csrf) ->
        		csrf.disable())
        	.cors((cors) ->
        		cors.configurationSource(corsConfigurationSource()))
        	.authorizeHttpRequests((authorizeRequests) ->
        		authorizeRequests
        			.requestMatchers("/user/*").permitAll()
        			.requestMatchers("/main").permitAll()
        			.requestMatchers("/board/*").hasRole("USER"))
        	.sessionManagement((sessionManagement) ->
        		sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        	.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        	.exceptionHandling((exceptionHandling) ->
        		exceptionHandling
        			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        			.accessDeniedHandler(jwtAccessDeniedHandler))
        	.apply(new JwtSecurityConfig(tokenProvide));
        		
        	
		return http.build();
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
    
}
