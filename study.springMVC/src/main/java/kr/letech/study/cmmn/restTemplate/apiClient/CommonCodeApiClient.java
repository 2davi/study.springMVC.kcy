/**
 * 
 */
package kr.letech.study.cmmn.restTemplate.apiClient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kr.letech.study.cmmn.code.vo.CommonCodeVO;
import kr.letech.study.cmmn.restTemplate.envelope.Envelope;
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
public class CommonCodeApiClient {

	private RestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:8081/cmmn-management";

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Envelope<List<CommonCodeVO>> readCmmnCdList(String cmmnGrpCd) {
		log.debug("▩▩▩ REST-API CommonCodeApiClient.readCmmnCdList( <CmmnGrpCd> ) 호출.");
		
		String url = BASE_URL + "/code/{cmmnGrpCd}";
		
		ResponseEntity<Envelope<List<CommonCodeVO>>> response = 
				restTemplate.exchange(
						url
						, HttpMethod.GET
						, null
						, new ParameterizedTypeReference<Envelope<List<CommonCodeVO>>>() {}
						, cmmnGrpCd
		);
		
		return response.getBody();
	}
}













