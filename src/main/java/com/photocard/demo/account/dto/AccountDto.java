package com.photocard.demo.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
		
	@NotBlank
	@Length(min = 3, max =20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
	private String username;
	
	@NotBlank
	@Length(min = 8, max = 50)
	private String password;
	
	@NotBlank
	@Email
	private String email;
	
	private String token;
	
	//생성 예제 -> localhost:8080/account/join ()
	// userid : wook , password : 123, email : wook@naver.com
}
