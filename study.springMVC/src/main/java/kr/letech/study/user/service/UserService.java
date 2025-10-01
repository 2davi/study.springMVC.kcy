/**
 * 
 */
package kr.letech.study.user.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
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
 *  2025-09-29		KCY				서비스 통합 작업
 */
public interface UserService {
	
	public void readUserList(Model model, UserDetailsVO loginUser, Integer keyword, String term);
	public UserVO readUserDetail(Model model, UserDetailsVO loginUser, String userId);
	public String createUser(Model model, UserDetailsVO loginUser, UserVO user, List<String> userRoles, MultipartFile multipart);
	public String modifyUser(Model model, UserDetailsVO loginUser, UserVO user, List<String> userRoles, MultipartFile multipart);
	public void removeUser(String userId, UserDetailsVO loginUser);
	
	public String removeProfileImg(String userId);
	
	
	
	public List<String> readUserRoles(String userId);
}
