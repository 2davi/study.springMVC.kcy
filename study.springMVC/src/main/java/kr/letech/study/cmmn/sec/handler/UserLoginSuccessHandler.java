/**
 * 
 */
package kr.letech.study.cmmn.sec.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest ;
import org.springframework.stereotype.Component;

import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
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
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);
		UserDetailsVO detailsVO = (UserDetailsVO) authentication.getPrincipal();
		session.setAttribute("userVO", detailsVO.getUserVO());
		
		//IP,SESSION ID
		WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
		log.debug("IP: {}", web.getRemoteAddress());
		log.debug("Session ID: {}" , web.getSessionId());
		
		//인증ID
		log.debug("name: {}", authentication.getName());
		
		//권한리스트
										  @SuppressWarnings("unchecked")
		List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
		StringBuffer sb = new StringBuffer();
		sb.append("권한: ");
		for(int i=0; i< authList.size(); i++) {
			sb.append(authList.get(i).getAuthority() + " ");
		}
		log.debug(sb.toString());
		
		
		//디폴트 URI
		String uri = "/";
		
		//강제 인터셉트 당했을 경우 데이터GET
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		//로그인 버튼 눌러 접속했을 경우 데이터GET
		//"prevPage"라는 속성은 로그인페이지에서 요정보낼 때 추가해놓으니까.
		String prevPage = (String) request.getSession().getAttribute("prevPage");
		
		if(prevPage != null) {
			request.getSession().removeAttribute("prevPage");
		}
		
		//null이 아니라면 강제 인터셉트 당했다는 것
		if(savedRequest != null) {
			uri = savedRequest.getRedirectUrl();
			
			//""가 아니라면 직접 로그인 페이지로 접속한 것
		} else if(StringUtils.isNotBlank(prevPage)) {
			uri = prevPage;
		}
		
		//세 가지 케이스에 따른 URI 주소로 리다이렉트
		response.sendRedirect(uri);	
	}
}
