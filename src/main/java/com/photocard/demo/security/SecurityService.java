//package com.photocard.demo.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.photocard.demo.account.Account;
//import com.photocard.demo.account.AccountRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class SecurityService implements UserDetailsService{
//
//	@Autowired AccountRepository accountRepository;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		//TODO 반환형 Account 말고 Optional로 바꾸기
//		Account account = accountRepository.findByUsername(username);
//		log.info("디테일즈 서비스");
//		log.info(username);
//		log.info(account.getUsername());
//		
//		if(account == null) {
//			throw new UsernameNotFoundException(username);
//		}
//		
//		return User.builder()
//					.password(account.getPassword())
//					.username(account.getUsername())
//					.roles(account.getRole())
//					.build();
//	}
//
//}
