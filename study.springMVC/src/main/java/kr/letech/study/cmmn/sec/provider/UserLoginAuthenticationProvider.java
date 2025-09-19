/**
 * 
 */
package kr.letech.study.cmmn.sec.provider;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kr.letech.study.cmmn.sec.service.CustomUserDetailsService;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * < 개정이력 >
 * 
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-16		KCY				최초 생성
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserLoginAuthenticationProvider implements AuthenticationProvider {
	private final CustomUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	
	//로그인처리
	@Override
	public Authentication authenticate(Authentication authentication) {
		/* 사용자가 입력한 정보 */
		String userId = authentication.getName();
		String userPw = (String) authentication.getCredentials();
		
		//DB에서 가져온 정보 커스터마이징가능))
		UserDetailsVO userDetails = (UserDetailsVO) this.userDetailsService.loadUserByUsername(userId);
		
		
		log.error("입력 PW: " + userPw);
		log.error("DB PW: " + userDetails.getPassword());
		log.error("matches? " + passwordEncoder.matches(userPw, userDetails.getPassword()));
		
		//인증 진행
		//DB에 정보가 없는 경우 예외 발생(아이디/비밀번호 잘못됐을 때와 동일한 예외로 처리하는 것 좋음)
		// ID PW 체크해서 안 맞는 경우(matches 이용)
		if(userDetails == null || !userId.equals(userDetails.getUsername())
				|| !this.passwordEncoder.matches(userPw, userDetails.getPassword())) {
			throw new BadCredentialsException(userId);
		
			//계정일치하면 나머지 부가 메소드 체크 //
			//잠긴 계정일 경우
		} else if(!userDetails.isAccountNonLocked()) {
			throw new LockedException(userId);
			
			//비활성화된 계정일 경우
		} else if(!userDetails.isEnabled()) {
			throw new DisabledException(userId);
			
			//만료된 계정일 경우
		} else if(!userDetails.isAccountNonExpired()) {
			throw new AccountExpiredException(userId);
			
			//비밀번호 만료된 경우
		} else if(!userDetails.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(userId);
		}
		
		//다썼으면 패스워드 지워줌(객체 계속 사용하니까)
		userDetails.setPassword(null);
		
		log.debug("▩▩▩▩▩PROVIDER▩▩▩▩▩▩▩▩▩ USER_DETAILS : {} - {}", userDetails.getUsername(), userDetails.getPassword());
		//최종 리턴시킬 새로만든 Authentication객체
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		
		return newAuth;
	}

	//로그인 처리 지원 여부
	@Override
	public boolean supports(Class<?> authentication) {
		// 스프링시큐리티가 요구하는 UPAToken 타입이 옳은지 확인
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	

}
