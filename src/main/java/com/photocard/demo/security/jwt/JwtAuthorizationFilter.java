package com.photocard.demo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.photocard.demo.account.Account;
import com.photocard.demo.account.AccountRepository;
import com.photocard.demo.security.PrincipalDetail;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	@Autowired
	public AccountRepository accountRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository) {
		super(authenticationManager);
		this.accountRepository = accountRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String jwtHeader = request.getHeader("Authorization");

		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}

		String token = request.getHeader("Authorization").replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(token).getClaim("username").asString();
		
		System.out.println("jwtAuthorization_username : " + username);
		
		if(username != null) {
			Account account = accountRepository.findByUsername(username);
			
			PrincipalDetail principalDetails = new PrincipalDetail(account);
			
			Authentication authentication =
					 new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		System.out.println("?????????..");
	
	}

}
