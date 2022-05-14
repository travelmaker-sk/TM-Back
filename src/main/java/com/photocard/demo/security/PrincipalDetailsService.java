package com.photocard.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.photocard.demo.account.Account;
import com.photocard.demo.account.AccountRepository;


@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		
		if(account == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new PrincipalDetail(account);
	}
}