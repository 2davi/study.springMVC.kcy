/**
 * 
 */
package kr.letech.study.cmmn.sec.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.letech.study.cmmn.file.service.FileService;
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
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cmmn/sec/loginForm")
public class LoginController {
	
	private final FileService fileService;
	
	/**
	 * 로그인 화면
	 * @param request
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String loginView(HttpServletRequest request) {
		log.debug("▩▩▩ URL: /cmmn/sec/loginForm (LoginController) 연결.");
		
		//요청 시점의 uri 정보를 session attribute로 담아서 전달(지워줘야하고)
		//로그인 틀려서 다시 할 때 요청시점의 uri가 로그인페이지가 되므로 조건문 설정
		//->> 로그인을 만들 때 고려하게 될 분기점들을 파악해두고 조건처리를 어떻게 하는지 익혀둔다.
		String uri = request.getHeader("Referer");
		if(StringUtils.isNotBlank(uri)&&!uri.contains("/cmmn/sec/loginForm")) {
			request.getSession().setAttribute("prevPage", request.getHeader("Referer"));
		}
		
		return "cmmn/sec/loginForm.popups";
	}

}
