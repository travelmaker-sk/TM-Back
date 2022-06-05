package com.notice.board.account;

import com.notice.board.mail.EmailMessage;
import com.notice.board.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	//private final JavaMailSender javaMailSender;
	private final EmailService emailService;
	
	
	//회원가입
	public Account join(Account account) {
		//validation으로 빼야함.
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
		
		//저장
		Account authResult = accountRepository.save(account);
		
		//메일 보내기
		EmailMessage emailMessage = EmailMessage.builder()
				.to(authResult.getEmail())
				.subject("TM 회원가입 인증 메일")
				.message("/check-email-token?token=" + authResult.getEmailCheckToken() +
				"&email=" + authResult.getEmail())
				.build();
		
		emailService.sendEmail(emailMessage);
		
		return authResult;
	}
	

	
}
