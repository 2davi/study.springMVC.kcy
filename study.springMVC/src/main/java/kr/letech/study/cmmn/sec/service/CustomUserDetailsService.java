/**
 * 
 */
package kr.letech.study.cmmn.sec.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.letech.study.cmmn.restTemplate.apiClient.SecurityApiClient;
import kr.letech.study.cmmn.restTemplate.apiClient.UserApiClient;
import kr.letech.study.cmmn.restTemplate.apiClient.UserRoleApiClient;
import kr.letech.study.cmmn.sec.dao.CustomUserDetailsDAO;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
import kr.letech.study.user.vo.UserVO;
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
@Service @Slf4j @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final CustomUserDetailsDAO userDetailsDAO;
	private final UserApiClient userApi;
	private final UserRoleApiClient userRoleApi;
	private final SecurityApiClient securityApi;

	@Override
	public UserDetails loadUserByUsername(String inputUserId) throws UsernameNotFoundException {
		
		//최종적으로 리턴할 객체
		UserDetailsVO userDetails = new UserDetailsVO();
		
		//사용자정보 select
//		UserVO userVO = this.userDetailsDAO.selectUser(inputUserId);
		//0930_야매
		UserVO userVO = userApi.readUserDetail(inputUserId).getData();
		
		//사용ㅈ정보 없으면 null
		if(userVO == null) {
//			return null;
// 그냥 500에러 발생해버림...
		    throw new UsernameNotFoundException("User not found with id: " + inputUserId);

			//사용자정보 있으면 로직전개(Details에 데이터 넣기)
		} else {
			userDetails.setUsername(userVO.getUserId());
			userDetails.setPassword(userVO.getUserPw());
			userDetails.setUserVO(userVO);
			userDetails.getUserVO().setUserPw(null);
			log.debug("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩ USER_DETAILS : {} - {}", userDetails.getUsername(), userDetails.getPassword());
			//사용자권한 select해서 받아온 List<String> 주입
//			userDetails.setAuthorities(this.userDetailsDAO.selectUserAuthList(inputUserId));
			
			//0930_야매
			userDetails.setAuthoritiesForVO(userRoleApi.readRolesOfUser(inputUserId).getData());
			
		}
		return userDetails;
	}
	
	
}













