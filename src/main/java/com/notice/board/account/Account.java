package com.notice.board.account;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//TODO 이거를  username으로 바꿔야 하나..
	@Column(unique = true)
	private String username;
	
	private String password;
	
	private String email;
	
	private boolean emailVerified;
	
	private String emailCheckToken; //TODO 인티저로 바꾸기
	
	private LocalDateTime JoinedAt;
	
	
	private String role; //USER, ADMIN
	
	
	public List<String> getRoleList(){
		if(this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}
	
	public void temporaryPassword() {
		String temp = UUID.randomUUID().toString();
		this.password = temp.substring(0, 6);
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
	
	public void generateEmailCheckToken() {
		int sdsd = (int)(Math.random()*999999);
		this.emailCheckToken = Integer.toString(sdsd);
		//this.emailCheckToken = UUID.randomUUID().toString();
	}
}
