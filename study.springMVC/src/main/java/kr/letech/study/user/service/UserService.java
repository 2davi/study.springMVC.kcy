/**
 * 
 */
package kr.letech.study.user.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.vo.SearchVO;
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
 *  2025-09-15		KCY				검색 서비스 추가
 *  2025-09-16		KCY				권한 조회 서비스 추가
 */
public interface UserService {
	public UserVO readUserDetail(String userId);
	public void createUser(UserVO user, List<String> userRoles);
	public String createUser(Model model, UserVO user, List<String> userRoles, MultipartFile multipart);
	public void modifyUser(UserVO user, List<String> userRoles);
	public String modifyUser(Model model, UserVO user, List<String> userRoles, MultipartFile multipart);
	public void removeUser(String userId);
	public String removeProfileImg(String userId);
	
	
//	public List<UserVO> readUserListBySearch(String search);
	public List<UserVO> readUserList(SearchVO search);
	
	public List<String> readUserRoles(String userId);
}
