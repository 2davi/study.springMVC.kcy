/**
 * 
 */
package kr.letech.study.cmmn.restTemplate.error;

import java.util.Map;

/**
 * 
 * <pre>
 * 	RFC 7807의 Problem Details를 흉내낸 클래스
 * 	(Spring 6의 ProblemDetail 대체)
 * </pre>
 * 
 * < 개정이력 >
 * 
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-29		KCY				최초 생성
 */
public class ProblemDetail {
	private String type;
	private String title;
	private int status;
	private String detail;
	private String instance;
	private Map<String, Object> properties;
	
	public ProblemDetail(String type
				 , String title
				 , int status
				 , String detail
				 , String instance
				 , Map<String, Object> properties) {
		this.type = type;
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.instance = instance;
		this.properties = properties;
	}
	
	// Getter & Setter
	public String getType() { return type; }
	public String getTitle() { return title; }
	public int getStatus() { return status; }
	public String getDetail() { return detail; }
	public String getInstance() { return instance; }
	public Map<String, Object> getProperties() { return properties; }
}
