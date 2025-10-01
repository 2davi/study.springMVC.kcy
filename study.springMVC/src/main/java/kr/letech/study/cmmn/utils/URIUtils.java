/**
 * 
 */
package kr.letech.study.cmmn.utils;

import java.util.Iterator;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

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
public class URIUtils {

	public static String makeQueryString(Map<String, Object> params) {
		if(params == null || params.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		
		sb.append("?");
		while(iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			sb.append(entry.getKey())
			  .append("=")
			  .append(entry.getValue() == null ? "" : entry.getValue().toString());
			
			if(iterator.hasNext()) {
				sb.append("&");
			}
		}
		
		return sb.toString();
	}
	
	public static String makePathVariables(Map<String, Object> params) {
		if(params == null || params.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			sb.append("/");
			sb.append(entry.getValue() == null ? "" : entry.getValue().toString());
		}
		
		return sb.toString();
	}
	
	public static String makeURI(String baseUrl, String handlerMappingUrl) {
		return baseUrl + handlerMappingUrl;
	}
	
	public static String makeURIWithQueryStrings(String baseUrl, String handlerMappingUrl, Map<String, Object> qsParams) {
		String queryStrings = makeQueryString(qsParams);
		return baseUrl + handlerMappingUrl + queryStrings;
	}
	
	public static String makeURIWithPathVariables(String baseUrl, String handlerMappingUrl, Map<String, Object> pvParams) {
		String pathVariables = makePathVariables(pvParams);
		return baseUrl + handlerMappingUrl + pathVariables;
	}


	public static String makeURIWithPathVariables2(String baseUrl, String handlerMappingUrl, Map<String, ?> pvParams) {
		UriComponentsBuilder b = UriComponentsBuilder.fromHttpUrl(baseUrl).path(handlerMappingUrl);
		
		if(pvParams == null || pvParams.isEmpty()) {
			return b.build().toUriString();
		}
		return b.buildAndExpand(pvParams).encode().toUriString();
	}

	//0930_경로변수, 쿼리스트링 하나씩 쓰는 URI
	public static String makeURIWithPathAndQuery(
			String baseUrl,
			String handlerMappingUrl,
			Map<String, ?> pathVars,
			Map<String, ?> queryParams) {
		
		// 1. PathVariable 치환
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(baseUrl)
				.path(handlerMappingUrl);
		
		String uriString;
		if (pathVars != null && !pathVars.isEmpty()) {
			uriString = builder.buildAndExpand(pathVars).toUriString();
		} else {
			uriString = builder.build().encode().toUriString();
		}
		
		// 2. QueryString 추가
		UriComponentsBuilder withQuery = UriComponentsBuilder.fromUriString(uriString);
		
		if (queryParams != null && !queryParams.isEmpty()) {
			queryParams.forEach((k, v) -> withQuery.queryParam(k, v));
		}
		
		return withQuery.encode().toUriString();
	}
}
