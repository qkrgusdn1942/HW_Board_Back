package com.hw.common;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private TokenProvide tokenProvide;
	
	public JwtSecurityConfig (TokenProvide tokenProvide) {
        this.tokenProvide = tokenProvide;
}

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvide);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
	
}
