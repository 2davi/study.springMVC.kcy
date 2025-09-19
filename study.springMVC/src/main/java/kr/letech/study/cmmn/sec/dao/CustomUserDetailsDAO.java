/**
 * 
 */
package kr.letech.study.cmmn.sec.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.user.vo.UserVO;

/**
 * 
 * <pre>
 * Spring Security 사용자 조회 DAO
 * </pre>
 * 
 * < 개정이력 >
 * 
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-16		KCY				최초 생성
 */
@Mapper
public interface CustomUserDetailsDAO {
	
	/**
	 * 사용자 정보 조회
	 * @param userId
	 * @return
	 */
	UserVO selectUser(String userId);
	
	/**
	 * 사용자 권한 목록 조회
	 * @param userId
	 * @return
	 */
	List<String> selectUserAuthList(String userId);
}
