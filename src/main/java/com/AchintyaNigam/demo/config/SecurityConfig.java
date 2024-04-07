package com.AchintyaNigam.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.AchintyaNigam.demo.filter.JwtAuthFilter;
import com.AchintyaNigam.demo.service.UserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	 private JwtAuthFilter authFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	  @Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }
	

	
	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.withUsername("Achi")
//				.password(encoder.encode("1234"))
//				.roles("admin")
//				.build();
//		UserDetails student = User.withUsername("Achi2")
//				.password(encoder.encode("1234"))
//				.roles("student")
//				.build();
//		
//		return new InMemoryUserDetailsManager(admin, student);
		return new UserInfoUserDetailsService();
	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/login/**").permitAll()
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/api/student/address/post").permitAll()
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/api/profile/post").permitAll()
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/api/student/profile/post").permitAll()
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/api/teacher/profile/post").permitAll()
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/api/**").authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authenticationProvider(authenticationProvider())
		.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
		.build();
}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
	
}