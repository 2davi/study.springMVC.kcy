/**
 * 
 */
package kr.letech.study.cmmn.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.letech.study.cmmn.code.vo.CommonCodeVO;

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
@Mapper
public interface CommonCodeDAO {
	public List<CommonCodeVO> selectCodeList(@Param(value="cmmnCodeParams") List<String> cmmnCodeParams);
}
