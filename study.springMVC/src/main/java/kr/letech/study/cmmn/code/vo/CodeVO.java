/**
 * 
 */
package kr.letech.study.cmmn.code.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter @Setter @ToString(callSuper = true)
public class CodeVO {
	private String id;
	private String cdNm;
}
