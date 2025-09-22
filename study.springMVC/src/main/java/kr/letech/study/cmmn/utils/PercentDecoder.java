package kr.letech.study.cmmn.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import kr.letech.study.cmmn.vo.SearchVO;
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
 *  2025-09-17		KCY				최초 생성
 *  2025-09-22		KCY				UTF-8 디코딩 오류 수정
 *  								SearchVO 반환 메소드 작성
 */
@Component @Slf4j
public class PercentDecoder {
    public static String decode(String encoded) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encoded.length();) {
            char c = encoded.charAt(i);
            if (c == '%') {
                String hex = encoded.substring(i+1, i+3);
                int value = Integer.parseInt(hex, 16);
                sb.append((char) value);
                i += 3;
            } else if (c == '+') {
                sb.append(' ');
                i++;
            } else {
                sb.append(c);
                i++;
            }
        }
    /*	이 녀석이 한글을 자꾸 꺠먹고 있었다. 아이고;;
     * 
     *	URL 인코딩 된 문자열은 UTF-8을 16진수로 표현한 것으로,
     *	이 변환된 값을 ISO-8859-1 표기로 직접 바꾸는 것도 문제고(1),
     *	그래놓고 UTF-8로 또 변환하려 드니 더 문제.
     */	return new String(sb.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

    }
    
    
	/**
	 * <i>kr.letech.study.cmmn.utils.PercentDecoder.decodeURLSafe()</i><br />
	 * <b>퍼센트 인코딩<i>("%xx")</i>된 문자열을 UTF-8로 디코딩한다.</b><br />
	 * <br />
	 * 
	 * @param encoded 퍼센트 인코딩된 문자열
	 * @retirm UTF-8 문자열
	 */
    public static String decodeURLSafe(String encoded) {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	
    	for(int i=0; i < encoded.length();) {
    		Character c = encoded.charAt(i);
    		
    		// "%xx" -> 16진수 변환 -> Byte로 작성
    		if(c == '%') {
    			String hex = encoded.substring(i+1, i+3);
    			Integer value = Integer.parseInt(hex, 16);
    			baos.write(value);
    			i += 3;
    		
    		// URL-safe SPEC에서 '+'는 공백 처리
    		} else if (c == '+') {
    			baos.write(' ');
    			i++;
    		
    		// 일반 문자 -> UTF-8로 변환 -> Byte로 작성
    		} else {
    			byte[] utf8 = String.valueOf(c).getBytes(StandardCharsets.UTF_8);
    			baos.write(utf8, 0, utf8.length);
    			i++;
    		}
    	}
    	
    	// 최종 :: UTF-8로 디코딩된 문자열 생성
    	return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }
    
    
    /**
     * <i>kr.letech.study.cmmn.utils.PercentDecoder.searchParamValidate()</i><br />
     * <b>SearchBox를 통한 검색 필터를 반환한다.</b><br />
	 * <br />
	 * 
     * @param keyword
     * @param term
     * @return
     */
    public static SearchVO searchParamValidate(Integer keyword, String term) {
		
    	//--- 파라미터 초기화
		if(keyword != 0 && keyword != 1 && keyword != 2) {
			keyword = 0;
			term = "default";
			
		//--- 파라미터 디코딩
		} else {
			term = decodeURLSafe(term);
		}
		
		log.debug("▩ SEARCH-BOX: KEYWORD = \"{}\", TERM = \"{}\"", keyword, term);
		SearchVO search = new SearchVO(keyword, term);
		
		return search;
    }
}
