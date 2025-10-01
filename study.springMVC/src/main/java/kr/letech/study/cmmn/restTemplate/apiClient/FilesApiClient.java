/**
 * 
 */
package kr.letech.study.cmmn.restTemplate.apiClient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kr.letech.study.cmmn.restTemplate.envelope.Envelope;
import kr.letech.study.cmmn.utils.URIUtils;
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
 *  2025-10-01		KCY				최초 생성
 */
@Slf4j
public class FilesApiClient {

	private RestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:8081/file-management";
	
	// setter For XML Beans Settings...
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Envelope<List<String>> readFileRefNmList(String fileGrpId) {
		log.debug("▩▩▩ REST-API FilesApiClient.readFileRefNmList() 호출.");
		
		String url = URIUtils.makeURI(BASE_URL, "/files/ref-nm/{fileGrpId}");
		
		// 제네릭 타입 추론 중
		ResponseEntity<Envelope<List<String>>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.GET
						, null
						, new ParameterizedTypeReference<Envelope<List<String>>>() {}
						, fileGrpId
		);
		
		return response.getBody();	
	}
}
