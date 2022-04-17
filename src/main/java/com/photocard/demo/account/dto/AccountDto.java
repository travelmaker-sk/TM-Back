package com.photocard.demo.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
		
	private String username;
	
	private String password;
	
	private String email;
	
	//생성 예제 -> localhost:8080/account/join ()
	// userid : wook , password : 123, email : wook@naver.com
}
