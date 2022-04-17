package com.photocard.demo.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.catalina.filters.AddDefaultCharsetFilter.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photocard.demo.account.Account;
import com.photocard.demo.security.PrincipalDetail;

import lombok.RequiredArgsConstructor;


//토큰 주는곳 
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	//@Autowired
	private final AuthenticationManager authenticationManager;

	//login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			ObjectMapper om = new ObjectMapper();
			Account account = om.readValue(request.getInputStream(), Account.class);
			
			//우리가 입력한 비밀번호 값
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
			
			//PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			//authentication객체가 세션에 저장됨 -> 로그인 됨
			PrincipalDetail principalDetails = (PrincipalDetail)authentication.getPrincipal();
			return authentication;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		PrincipalDetail principalDetails = (PrincipalDetail)authResult.getPrincipal();
		String jwtToken = JWT.create()
				.withSubject("wookToken")
				.withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
				.withClaim("id", principalDetails.getAccount().getId())
				.withClaim("username", principalDetails.getAccount().getUsername())
				.sign(Algorithm.HMAC512("cos"));
		
		response.addHeader("Authorization","Bearer " + jwtToken);	
		//response로 body값 주는 방법?
		//HttpServletResponseWrapper
//		response.setContentType("application/json");
//		response.setCharacterEncoding("utf-8");
//		
//		Account account = new Account();
//		account.setUsername("woooook");
//		account.setPassword("12312312");
//		account.setRole("USER");
//		
//		
//		ObjectMapper sm = new ObjectMapper();
//		//Account account = om.readValue(request.getInputStream(), Account.class);
//		
//		String result = sm.writeValueAsString(account);
//		System.out.println(result);
//        response.getWriter().write(result);
		
	}
	
	
	
}
