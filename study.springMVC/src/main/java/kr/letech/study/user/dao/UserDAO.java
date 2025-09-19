/**
 * 
 */
package kr.letech.study.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.cmmn.vo.SearchVO;
import kr.letech.study.cmmn.vo.UserRoleVO;
import kr.letech.study.user.vo.UserVO;

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
 *  2025-09-12		KCY				최초 생성
 *  2025-09-15		KCY				검색 매퍼 추가
 *  2025-09-16		KCY				비밀번호 암호화 매퍼 추가,
 *  								권한 CRUD 매퍼 추가
 */
@Mapper
public interface UserDAO {
	public UserVO selectUserDetail(String userId);
	public Integer insertUser(UserVO user);
	public Integer updateUser(UserVO user);
	public Integer deleteUser(String userId);
	
	public List<UserVO> selectUserListBySearch(String search);
	public List<UserVO> selectUserList(SearchVO search);
	
	public List<String> selectUserRoles(String userId);
	
	/**
	 * <i>kr.letech.study.cmmn.user.service.UserServiceImpl.createUser()</i><br />
	 * <b>서비스에서 반복문 돌리게 냅두고 나는 하나씩만 옮긴다.</b><br /><br />
	 * 
	 * @param userRole
	 * @return
	 */
	public Integer mergeUserRole_OLD(UserRoleVO userRole);
	
	/**
	 * <i>kr.letech.study.cmmn.user.service.UserServiceImpl.createUser()</i><br />
	 * <b>권한리스트 몽땅 들고 단번에 upsert 1회 수행.</b><br />
	 * + 이미 삭제되었던 권한을 다시 부여받는 경우,<br />&nbsp;&nbsp;&nbsp;
	 *   update 분기를 타면서 DEL_YN = 'N'으로 어련히 활성화 됨.<br /><br />
	 * @param forEachParams
	 * - <String> userId, [rgstId,] [updtId,] <List> userRoles
	 * @return
	 */
	public Integer mergeUserRole(Map<String, Object> forEachParams);
	
	
	/**
	 * <i>kr.letech.study.cmmn.user.service.UserServiceImpl.createUser()</i><br />
	 * <b>수정으로 잃은 권한 논리삭제.</b><br /><br />
	 * @param forEachParams 
	 * - <String> userId, [rgstId,] [updtId,] <List> userRoles
	 * @return
	 */
	public Integer deleteUserRole(Map<String, Object> forEachParams);
	
	public Integer deleteProfileImg(String userId);
}