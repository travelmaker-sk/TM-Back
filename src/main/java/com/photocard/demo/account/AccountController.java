package com.photocard.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.photocard.demo.account.dto.AccountDto;
import com.photocard.demo.account.dto.ResponseDto;

@RestController
public class AccountController {
	
	@Autowired AccountService accountService;
	
	
	//회원가입
	@PostMapping("/account/join")
	public ResponseEntity<?> join(@RequestBody AccountDto accountDto){
		try {
			//캡슐화(Dto -> Entity)
			Account account = Account.builder()
					.email(accountDto.getEmail())
					.username(accountDto.getUsername())
					.password(accountDto.getPassword())
					.role("USER")
					.build();		
			
			System.out.println(account.getRole());
			//저장한 Account리턴
			Account authResult = accountService.join(account);
			
			//캡슐화(Dto -> Entity)
			AccountDto dto = AccountDto.builder()
					.email(authResult.getEmail())
					.username(authResult.getUsername())
					.build();
			
			return ResponseEntity.ok().body(dto);		
			
		}catch(Exception e){
			ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	//유저 테스트
	@GetMapping("/user")
	public ResponseEntity<?> user(){
		Account ac = new Account();
		ac.setUsername("wook");
		ac.setPassword("user");
		ac.setRole("USER");
		return ResponseEntity.ok().body(ac);
	}
	
	//여기서 리턴값을 받아올 순 없나?
	//자유권한
	@GetMapping("/manager")
	public ResponseEntity<?> manager() {
		AccountDto ac = new AccountDto();
		ac.setPassword("manager");
		System.out.println(ac);
		return ResponseEntity.ok().body(ac);	
	}

	
}
