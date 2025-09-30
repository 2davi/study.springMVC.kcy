/**
 * 
 */
package kr.letech.study.cmmn.restTemplate.apiClient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kr.letech.study.cmmn.restTemplate.envelope.Envelope;
import kr.letech.study.cmmn.utils.URIUtils;
import kr.letech.study.cmmn.vo.UserRoleVO;
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
public class UserRoleApiClient {
	
	private RestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:8081/cmmn-management";
	// setter For XML Beans Settings...
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Envelope<List<UserRoleVO>> readRolesOfUser(String userId) {
		log.debug("▩▩▩ REST-API UserRoleApiClient.readRolesOfUser() 호출.");
		
		String url = URIUtils.makeURI(BASE_URL, "/user-role/{userId}");
		
		ResponseEntity<Envelope<List<UserRoleVO>>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.GET
						, null
						, new ParameterizedTypeReference<Envelope<List<UserRoleVO>>>() {}
						, userId
		);
		
		return response.getBody();
	}
	
	public Envelope<Void> mergeUserRoles(String userId, List<String> userRoles, String username) {
		log.debug("▩▩▩ REST-API UserRoleApiClient.mergeUserRoles() 호출.");
		
		String url = URIUtils.makeURI(BASE_URL, "/user-role/{userId}?username={username}");
		
		HttpEntity<List<String>> httpEntity = new HttpEntity<>(userRoles);
		
		ResponseEntity<Envelope<Void>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.POST
						, httpEntity
						, new ParameterizedTypeReference<Envelope<Void>>() {}
						, userId
						, username
		);
		
		return response.getBody();
	}
}
