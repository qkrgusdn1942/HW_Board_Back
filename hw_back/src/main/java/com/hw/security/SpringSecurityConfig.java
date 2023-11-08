package com.hw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hw.common.JwtSecurityConfig;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
	 
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        	.csrf((csrf) ->
        		csrf.disable())
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
    
}
