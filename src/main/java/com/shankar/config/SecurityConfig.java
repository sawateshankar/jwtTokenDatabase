package com.shankar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shankar.util.JwtAuthenticationEntryPoint;
import com.shankar.util.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	   
 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((auth -> 
				auth.requestMatchers("/test")
				.authenticated()
				.requestMatchers("/auth/login").permitAll()
				.anyRequest()
				.authenticated()))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	
	

}
