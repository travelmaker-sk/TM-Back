package com.notice.board.mail;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data 
@Builder
public class EmailMessage {
	
	private String to;
	
	private String subject;
	
	private String message;
	
}
