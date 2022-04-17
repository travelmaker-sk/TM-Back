package com.photocard.demo.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.filter.CorsFilter;

import com.photocard.demo.account.AccountRepository;
import com.photocard.demo.security.jwt.JwtAuthenticationFilter;
import com.photocard.demo.security.jwt.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	public CorsFilter corsFilter;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//cors필터
		http.addFilter(corsFilter);
		http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
		http.addFilter(new JwtAuthorizationFilter(authenticationManager(),accountRepository));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용 x
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.csrf().disable();
		
		http.authorizeRequests()
				.antMatchers("/","/account/**","/index").permitAll()
				.antMatchers("/user").permitAll()
				.antMatchers("/manager").hasRole("USER")
				.antMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated();
				//.and()
		
		
		//로그인 하면 이 페이지로
		//http.formLogin();
		
		
		//디비설정
		http.headers().addHeaderWriter(
				new XFrameOptionsHeaderWriter(
						new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))
						)
			).frameOptions().sameOrigin();
	
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/h2-console/**");
	}
}
