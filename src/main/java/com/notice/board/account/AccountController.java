package com.notice.board.account;

import java.net.URI;
import java.time.LocalDateTime;

import javax.validation.Valid;

import com.notice.board.account.dto.AccountDto;
import com.notice.board.account.dto.KakaoOauthDto;
import com.notice.board.account.dto.OauthToken;
import com.notice.board.account.dto.ResponseDto;
import com.notice.board.mail.EmailMessage;
import com.notice.board.mail.EmailService;
import com.notice.board.security.PrincipalDetail;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
public class AccountController {

	private final AccountService accountService;
	private final AccountRepository accountRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

//	private final AuthenticationManager authenticationManager;

//	@Qualifier("AccountController")
//	private  AuthenticationManager authenticationManager;

//	@GetMapping("/")
//	public ResponseEntity<?> home(){
		//여기 안에서 컨텍스트 jwt안에 있는 토큰으로 값 확인해서 (그러려면 jwt인가에서 보내줘야함)
	//그냥 시큐리티 컨텍스트 홀더에서도 꺼내쓸 수 있을듯?
//		return ResponseEntity.ok().body("sd");
//	}
	
	
	//홈 기본사용자 확인
	@GetMapping("/account/mypage")
	public ResponseEntity<?> home(@RequestHeader HttpHeaders headers){
		System.out.println("헤더다 : " + headers);
		System.out.println("로그인 홈 ");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		if(principal.equals("anonymousUser")) {
			System.out.println("익명사용자");
		}else {
			System.out.println("로그인 유저 확인 1");
			PrincipalDetail principalDetail = (PrincipalDetail)principal;

			String username = principalDetail.getUsername();
			//이걸로 이제 이메일 불러오기
			Account account =accountRepository.findByUsername(username);
			System.out.println("로그인 유저 확인 2");
			System.out.println(account);
			return ResponseEntity.ok().body(account);
		}
		
		return ResponseEntity.ok().body(null);
	}
	
	// TODO 에러를 왜 받았지?
	// 회원가입
	@PostMapping("/account/join")
	public ResponseEntity<?> join(@Valid @RequestBody AccountDto accountDto, Errors errors) {
		try {

//			if(errors.hasErrors()) {
//				System.out.println("1번에러 : " + errors.getFieldError("nickname").getDefaultMessage());
//				System.out.println("2번에러 : " + errors.getFieldValue("nickname"));
//			}
			
			// 캡슐화(Dto -> Entity)
			Account account = Account.builder().email(accountDto.getEmail()).username(accountDto.getUsername())
					.password(accountDto.getPassword()).role("USER").build();

			// 토큰생성
			account.generateEmailCheckToken();
			
			///////////////////////////////////////////////////////////////////////
			
			// 저장한 Account리턴
//			Account authResult = accountService.join(account);
			
			//입력을 안함
			if(account == null || account.getUsername() == null) {
				System.out.println(account);
				return ResponseEntity.ok().body(account);
			}
			
			//아이디가 있지만 이미 있는 아이디인지 확인
			if(accountRepository.existsByUsername(account.getUsername())) {
				return ResponseEntity.ok().body(account);
			}
			
			//인코더
			account.encodePassword(passwordEncoder);
			
			//저장
			Account authResult = accountRepository.save(account);
			
			//메일 보내기
			EmailMessage emailMessage = EmailMessage.builder()
					.to(authResult.getEmail())
					.subject("TM 회원가입 인증 메일입니다.")
					.message("인증번호: "+authResult.getEmailCheckToken())
					.build();
			
			emailService.sendEmail(emailMessage);
			
			
			
			
			///////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////

			// 캡슐화(Entity -> Dto)
			AccountDto dto = AccountDto.builder().email(authResult.getEmail()).username(authResult.getUsername())
					.build();

			return ResponseEntity.ok().body(null);

		} catch (Exception e) {
			ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	//
	// 회원 가입 이메일 인증
	// rnbtbybcuffxraba(비번)
	@Transactional
	@PostMapping("/check-email-token")
	public ResponseEntity<?> checkEmailToken(@RequestBody AccountDto accountDto) {
		
		
		
		// 여기다가 토큰값 넣구 널 값 확인하고 이메일 확인하기
		System.out.println(accountDto.getToken()); //토큰값
		System.out.println(accountDto.getEmail()); //이메일
		String token = accountDto.getToken();

		Account account = accountRepository.findByEmail(accountDto.getEmail());
		System.out.println("어카운트 : " + account);
		System.out.println("어카운트 : " + accountDto);
		
		
		if(account == null) {
			account.setUsername("sulbin");
			return ResponseEntity.ok().body(account);
		}

		if (!account.getEmailCheckToken().equals(accountDto.getToken())) {
			String emailtokenError = "wrong.token";
//			return ResponseEntity.badRequest().body(accountDto);//
//			return ResponseEntity.ok(accountDto);//
			return new ResponseEntity<>(accountDto,HttpStatus.OK);
		}

		// 이메일 인증 트루
		account.setEmailVerified(true);
		account.setJoinedAt(LocalDateTime.now());
		
		return ResponseEntity.ok().body(null);
	}

	// 비밀번호 찾기
	@PostMapping("/account/findpassword")
	public ResponseEntity<?> findPassword(@RequestBody AccountDto accountDto) {
		// 이메일 입력
		// 이메일 보내기 그리고 비번 보낸걸로 바꿔놓기
		if(!accountRepository.existsByEmail(accountDto.getEmail())) {
			return ResponseEntity.ok().body(accountDto);
		}
		Account account = accountRepository.findByEmail(accountDto.getEmail());
//		if(account == null) {
//			account.setUsername("sulbin");
//			return ResponseEntity.ok().body(account);
//		}

		System.out.println("비번");
		// 임시 비번 생성
		account.temporaryPassword();

		EmailMessage emailMessage = EmailMessage.builder().to(account.getEmail()).subject("TM 임시 비밀번호 발급 메일입니다.")
				.message("임시 비밀번호: " + account.getPassword()).build();

		emailService.sendEmail(emailMessage);

		account.encodePassword(passwordEncoder);
		accountRepository.save(account);

		// 이메일 받고 비번 새로 바꾸기ㄴㅇ
		return ResponseEntity.ok().body(null);
	}
	
	//회원 탈퇴
	@Transactional
	@DeleteMapping("/account/delete")
	public ResponseEntity<?> deleteUser(){
		
		//유저정보 가져오기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("지우는중");
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		PrincipalDetail principalDetail = (PrincipalDetail)principal;
		
		
		//회원 있는지 확인하기
		Account account = accountRepository.findByUsername(principalDetail.getUsername());
		if(account == null) {
			return ResponseEntity.badRequest().body(account);
		}
		
		//확인하고 삭제하기
		if(account != null) {
			System.out.println("삭제 : " + account);
			accountRepository.deleteByUsername(account.getUsername());
		}
		
		return ResponseEntity.ok().body(null);
	}

	// 카카오 로그인 요청시 인증코드
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {

		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		// Retrofit2
		// Okhttp
		// RestTemplate

		RestTemplate restTemplate = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "a10968d091035e94e254188bf4ca528a");
		params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, header);

		System.out.println(kakaoRequest);

		// http 요청하기
		ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoRequest, String.class);

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		OauthToken oauthToken = null;

		try {
			oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("엑세스 코인 매퍼 : " + oauthToken.getAccess_token());

		// -------------------------------------------------------------------------------------------------------
		// 엑세스 토큰으로 요청하기...
		RestTemplate restTemplate2 = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders header2 = new HttpHeaders();
		header2.add("Authorization", "Bearer " + oauthToken.getAccess_token());// 여기다가 jwt토큰 넣으면 되는듯?
		header2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(header2);

		System.out.println(kakaoRequest);

		// http 요청하기
		ResponseEntity<String> response2 = restTemplate2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, String.class);

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper2 = new ObjectMapper();

		KakaoOauthDto kakaoOauthDto = null;

		try {
			kakaoOauthDto = objectMapper2.readValue(response2.getBody(), KakaoOauthDto.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 오스 디티오 : " + kakaoOauthDto.getId());
		System.out.println("카카오 오스 디티오 : " + kakaoOauthDto.getKakao_account().getEmail());
		System.out.println("카카오 오스 디티오 : " + kakaoOauthDto.getProperties().getNickname());

		// 아이디 있는지 찾기
		Account hasaccount = accountRepository.findByEmail(kakaoOauthDto.getKakao_account().getEmail());
		System.out.println("1아이디 조회 : " + hasaccount);

		Account authResult = null;

		// 아이디 없으면
		if (hasaccount == null) {
			System.out.println("2회원가입");
			// 회원가입 하기
//					.username(kakaoOauthDto.getKakao_account().getEmail()+"_"+kakaoOauthDto.getId())
//					.email(kakaoOauthDto.getProperties().getNickname()+"_"+kakaoOauthDto.getId())
			Account account = Account.builder().username(kakaoOauthDto.getKakao_account().getEmail()).password("teemo") // 프로퍼티스
																														// 공통비번
																														// 따로
																														// 설정하기
					.role("USER").build();

			account.encodePassword(passwordEncoder);

			System.out.println("3회원가입 : " + account);
			authResult = accountRepository.save(account);
			System.out.println("4회원가입 완료 : " + authResult);
		}

		System.out.println("5로그인 시작");
		// 토큰 만료시간 각각 체크해서 맞추기?
		// 아이디 있으면 로그인

		//---------------------------------------------------------------------------
		// 엑세스 토큰으로 요청하기...
		RestTemplate restTemplate3 = new RestTemplate();
//
//		// HttpHeader 오브젝트 생성
//		HttpHeaders header3 = new HttpHeaders();
//		header3.add("Content-type", "application/json;charset=utf-8");
//
//		MultiValueMap<String, String> paramLogin = new LinkedMultiValueMap<String, String>();
//		paramLogin.add("username", authResult.getUsername());
//		paramLogin.add("password", authResult.getPassword());
//		paramLogin.add("email", authResult.getEmail());
//		
//		System.out.println("카카오 로그인1 : " + paramLogin);
//		
//		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//		HttpEntity<MultiValueMap<String, String>> KakaoLogin = new HttpEntity<>(paramLogin, header3);
//		
////		ObjectMapper ob = new ObjectMapper();
////		String str=null;
////		try {
////			str = ob.writerWithDefaultPrettyPrinter().writeValueAsString(KakaoLogin);
////		} catch (JsonProcessingException e) {
////			e.printStackTrace();
////		}
////		
////		System.out.println("카카오 로그인2 : " + str);
//
//		// http 요청하기
//		ResponseEntity<?> response3 = restTemplate3.exchange(
//				"http://localhost:8080/login", 
//				HttpMethod.POST,
//				KakaoLogin, 
//				String.class);
//		
//		System.out.println("리스폰스3 : " + response3);

		//---------------------------------------------------------------------------------
		// create request body
		JSONObject request = new JSONObject();
		request.put("username", authResult.getUsername());
		request.put("password", "teemo");

		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

		// send request and parse result
		ResponseEntity<String> loginResponse = restTemplate3
		  .exchange("http://localhost:8080/login", HttpMethod.POST, entity, String.class);
		
		
		

//		Authentication authenticateAction = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(authResult.getUsername(), authResult.getPassword()));
//		System.out.println("6로그인 시작");
//		SecurityContextHolder.getContext().setAuthentication(authenticateAction);

		System.out.println("카카오 로그인 완료");
//		System.out.println("카카오 로그인 완료");

		return loginResponse.getHeaders().toString();
	}

	// ------------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------------
	// 장
	@GetMapping("/testuri")
	public ResponseEntity<?> testuri() {
		AccountDto ac = new AccountDto();
		ac.setPassword("manager");
		System.out.println(ac);
		return ResponseEntity.ok().body(ac);
	}
	@GetMapping("/user")
	public ResponseEntity<?> user() {
//		Account ac = new Account();
//		ac.setUsername("wook");
//		ac.setPassword("user");
//		ac.setRole("USER");

		return ResponseEntity.ok().body("sd");
	}

	// 여기서 리턴값을 받아올 순 없나?
	@GetMapping("/manager")
	public ResponseEntity<?> manager() {
		AccountDto ac = new AccountDto();
		ac.setPassword("manager");
		System.out.println(ac);
		return ResponseEntity.ok().body(null);
	}
}