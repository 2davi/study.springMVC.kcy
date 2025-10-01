/**
 * 
 */
package kr.letech.study.cmmn.restTemplate.envelope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 *	모든 "성공" 응답을 감싸는 표준 껍데기.
 *		type: 응답 유형.
 *		correlationId: 요청 추적용 ID. 필터에서 생성, 전파
 *		data: 실제 비즈니스 데이터(payload)
 *		links: 잘 모르겠음.
 *		meta: 페이징/정렬/서버버전 등의 메타데이터
 *	https://docs.oracle.com/en/java/javase/17/language/records.html
 *	https://dao-blog.tistory.com/m/51
 *	https://velog.io/@youngho0620/Spring-6%EC%9D%98-%EC%83%88%EB%A1%9C%EC%9A%B4-%EC%97%90%EB%9F%AC-%EC%9D%91%EB%8B%B5-%ED%91%9C%EC%A4%80-ProblemDetail
 *	https://velog.io/@hongxipyo/Spring-Boot-Rest-Template-%EC%9D%B4%EB%9E%80-nebttejt
 * </pre>
 * 
 * < 개정이력 >
 * 
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-29		KCY				최초 생성
 */
public final class Envelope<T> {
	
	private String type;
	private String correlationId;
	private T data;
	private Map<String, String> links;
	private Map<String, Object> meta;
	
	public Envelope() {}
	public Envelope(String type, String correlationId, T data, Map<String, String> links, Map<String, Object> meta) {
		this.type = type;
		this.correlationId = correlationId;
		this.data = data;
		this.links = links;
		this.meta = meta;
	}
	
	
	/** static Factory Methods */
	public static <T> Envelope<T> ok(String cid, T data) {
		return new Envelope<>(
				"success"
				, cid
				, data
				, Collections.<String, String>emptyMap()
				, Collections.<String, Object>emptyMap()
		);
	}
	public static <T> Envelope<T> created(String cid, T data, String selfUrl) {
		return new Envelope<>(
				"success"
				, cid
				, data
				, Collections.singletonMap("self", selfUrl)
				, Collections.singletonMap("httpStatus", 201)
		);
	}
	public static <T> Envelope<T> page(String cid, T data, Integer page, Integer size, Long total) {
		return new Envelope<>(
				"success"
				, cid
				, data
				, Collections.emptyMap()
				, Collections.unmodifiableMap(
						new HashMap<String, Object>() {
							private static final long serialVersionUID = 7195566840335656233L;

						{
							put("page", page);
							put("size", size);
							put("total", total);
						}})
		);
	}


    // --- Getter / Setter (Jackson이 필드 주입하려면 필요) ---
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCorrelationId() {
		return correlationId;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public Map<String, String> getLinks() {
		return links;
	}
	
	public void setLinks(Map<String, String> links) {
		this.links = links;
	}
	
	public Map<String, Object> getMeta() {
		return meta;
	}
	
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
}