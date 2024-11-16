package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.CorsBeanDefinitionParser;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jwt.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Bean
	public UserDetailsService getUserDetailService()
	{
		return new CustomUserDetailsService();
	}
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		 http
		 .csrf().disable()
		 .cors().disable()
		 .authorizeRequests()
		 .requestMatchers("/token").permitAll()
		 .anyRequest().authenticated()
		 .and()
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		 
		  return http.build();
		 
	    }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder()
	 {
		 return NoOpPasswordEncoder.getInstance();
	 }
	 

	

}
