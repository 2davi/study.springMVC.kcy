/**
 * 
 */
package kr.letech.study.cmmn.restTemplate.apiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kr.letech.study.cmmn.restTemplate.envelope.Envelope;
import kr.letech.study.cmmn.utils.URIUtils;
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
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-29		KCY				최초 생성
 */
@Slf4j
public class UserApiClient {

	private RestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:8081/user-management";
	
	// setter For XML Beans Settings...
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Envelope<List<UserVO>> readUserList(Integer keyword, String term) {
		log.debug("▩▩▩ REST-API UserApiClient.readUserList() 호출.");
		
		Map<String, Object> params = new HashMap<>();
		params.put("key", keyword);
		params.put("term", term);
		String url = URIUtils.makeURIWithQueryStrings(BASE_URL, "/users", params);
		
		// 제네릭 타입 추론 중
		ResponseEntity<Envelope<List<UserVO>>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.GET
						, null
						, new ParameterizedTypeReference<Envelope<List<UserVO>>>() {}
		);
		
		return response.getBody();	
	}
	
	public Envelope<UserVO> readUserDetail(String userId) {
		log.debug("▩▩▩ REST-API UserApiClient.readUserDetail() 호출.");
		
		String url = URIUtils.makeURI(BASE_URL, "/users/{userId}");
		
		ResponseEntity<Envelope<UserVO>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.GET
						, null
						, new ParameterizedTypeReference<Envelope<UserVO>>() {}
						, userId
		);
		
		return response.getBody();
	}
	
	public Envelope<UserVO> createUser(UserVO user) {
		log.debug("▩▩▩ REST-API UserApiClient.createUser() 호출.");
		
		String url = URIUtils.makeURI(BASE_URL, "/users");
		
		HttpEntity<UserVO> requestEntity = new HttpEntity<>(user);
		ResponseEntity<Envelope<UserVO>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.POST
						, requestEntity
						, new ParameterizedTypeReference<Envelope<UserVO>>() {}
		);
		
		return response.getBody();
	}
}
