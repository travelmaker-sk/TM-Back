package com.notice.board.security;

import com.notice.board.account.Account;
import com.notice.board.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



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