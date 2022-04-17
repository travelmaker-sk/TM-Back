package com.photocard.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	//회원가입
	public Account join(Account account) {
		//객체에 아이디 있는지 확인
		if(account == null || account.getUsername() == null) {
			
			System.out.println(account);
			log.info("객체에 아이디가 없거나 account객체가 들어가지 않음");
			throw new RuntimeException("객체에 아이디가 없거나 account객체가 들어가지 않음");
		}
		
		//아이디가 있지만 이미 있는 아이디인지 확인
		if(accountRepository.existsByUsername(account.getUsername())) {
			log.warn("이미 존재하는 아이디",account.getUsername());
			throw new RuntimeException("아이디가 이미 존재");
		}
		
		//인코더
		account.encodePassword(passwordEncoder);
		
		Account authResult = accountRepository.save(account);
		return authResult;
	}
	
}
