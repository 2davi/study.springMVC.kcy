/**
 * 
 */
package kr.letech.study.cmmn.sec.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.letech.study.cmmn.sec.annotation.CurrentUser;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
import kr.letech.study.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * < 개정이력 >
 * 
 * 수정일 수정자 수정내용 ------------------------------------------------ 2025-09-19 KCY
 * 최초 생성
 */
@Slf4j
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class)
				&& (parameter.getParameterType().equals(UserDetailsVO.class)
						|| parameter.getParameterType().equals(UserVO.class));
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsVO)) {
			return null;
		}

		UserDetailsVO userDetails = (UserDetailsVO) authentication.getPrincipal();

		if (parameter.getParameterType().equals(UserVO.class)) {
			return userDetails.getUserVO(); // UserVO로 받을 경우
		}
		return userDetails; // UserDetailsVO로 받을 경우
	}

}
