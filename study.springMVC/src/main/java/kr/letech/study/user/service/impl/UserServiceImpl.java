/**
 * 
 */
package kr.letech.study.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.file.service.FileService;
import kr.letech.study.cmmn.file.vo.FilesVO;
import kr.letech.study.cmmn.vo.SearchVO;
import kr.letech.study.user.dao.UserDAO;
import kr.letech.study.user.service.UserService;
import kr.letech.study.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
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
 *  2025-09-12		KCY				최초 생성
 *  2025-09-15		KCY				검색 서비스 추가
 *  2025-09-16		KCY				권한 조회 서비스 추가
 */
@Service @Slf4j @RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserDAO userDAO;

	private final FileService fileService;

	private final BCryptPasswordEncoder passwordEncoder;
	
	
	@Override
	public UserVO readUserDetail(String userId) {
		log.debug("▩▩▩ UserService .readUserDetail() 호출.");
		
		return userDAO.selectUserDetail(userId);
	}

	@Override
	@Transactional
	public void createUser(UserVO user, List<String> userRoles) {
		log.debug("▩▩▩ UserService .createUser() 호출.");
		
		// 1.사용자정보 USER 등록
		log.info("▩ USER insert 시작함");
		userDAO.insertUser(user);
		
		// 2.사용자권한정보 USER_ROLE 등록
		log.info("▩ USER_ROLE insert 시작함");
//		for(String role: userRoles) {
//			UserRoleVO userRole = new UserRoleVO(role, user.getUserId());
//			userDAO.mergeUserRole_OLD(userRole);
//			log.info("▩ USER_ROLE 반복횟수 1회 완료");
//		}
		Map<String, Object> forEachParams = new HashMap<>();
		forEachParams.put("userId", user.getUserId());
		forEachParams.put("rgstId", user.getRgstId());
		forEachParams.put("updtId", user.getUpdtId());
		forEachParams.put("userRoles", userRoles);
		userDAO.mergeUserRole(forEachParams);

	}
	
	@Override @Transactional
	public String createUser(Model model, UserVO user, List<String> userRoles, MultipartFile multipart) {
		log.debug("▩▩▩ UserService .createUser2() 호출.");
		
		// 0918_프로필사진 첨부기능.
		FilesVO filesVO = fileService.createFile(multipart, "AF010", user.getUserId());
		
		String profileGrpId = filesVO.getFileGrpId(); 
		if(profileGrpId == null) {
			model.addAttribute("Msg", "첨부파일 처리 중 오류가 발생했습니다.");
			return "redirect:/cmmn/user/insert";
		}
		//user 프로필사진 컬럼에 uuid 저장
		user.setProfileGrpId(profileGrpId);
		
		//Security: 암호화된 비밀번호 저장
		user.setUserPw(passwordEncoder.encode(user.getUserPw()));
		
		// 사용자 등록기능.
		createUser(user, userRoles);
		return "redirect:/";
	}


	@Override
	@Transactional
	public void modifyUser(UserVO user, List<String> userRoles) {
		log.debug("▩▩▩ UserService .modifyUser() 호출.");
		
		userDAO.updateUser(user);
		
		Map<String, Object> forEachParams = new HashMap<>();
		forEachParams.put("userId", user.getUserId());
		forEachParams.put("rgstId", user.getRgstId());
		forEachParams.put("updtId", user.getUpdtId());
		forEachParams.put("userRoles", userRoles);
		userDAO.mergeUserRole(forEachParams);
		userDAO.deleteUserRole(forEachParams);
		/*
		 * Query 안에서 IN() 절이나 foreach 문법을 쓸 일이 없다.
		 * delete-insert는 보통 물리삭제의 경우에 
		 */
	}

	@Override @Transactional
	public String modifyUser(Model model, UserVO user, List<String> userRoles, MultipartFile multipart) {
		log.debug("▩▩▩ UserService .modifyUser2() 호출.");
		
		String userId = user.getUserId();
		
		if(!multipart.isEmpty()) {
			log.debug("▩ ----- multipart가 존재한다.");
			
			UserVO oldUser = userDAO.selectUserDetail(userId);
			
			// 0918_프로필사진 첨부기능. +수정
			FilesVO filesVO = fileService.modifyFile(multipart, "AF010", user.getUserId(), oldUser.getProfileGrpId());
			
			String profileGrpId = filesVO.getFileGrpId(); 
			if(profileGrpId == null) {
				model.addAttribute("Msg", "첨부파일 처리 중 오류가 발생했습니다.");
				return "redirect:/cmmn/user/update?userId=" + userId;
			}
			//user 프로필사진 컬럼에 uuid 저장
			user.setProfileGrpId(profileGrpId);
		}
		
		//Security: 암호화된 비밀번호 저장
		user.setUserPw(passwordEncoder.encode(user.getUserPw()));
		
		modifyUser(user, userRoles);
		return "redirect:/cmmn/user/detail?userId=" + userId;
	}
	
	@Override @Transactional
	public void removeUser(String userId) {
		log.debug("▩▩▩ UserService .removeUser() 호출.");
		
		
		UserVO target = userDAO.selectUserDetail(userId);
		String profileGrpId = target.getProfileGrpId();
		
		Map<String, Object> forEachParams = new HashMap<>();
		forEachParams.put("userId", userId);
		forEachParams.put("updtId", userId);
		forEachParams.put("userRoles", null);
		
		userDAO.deleteUserRole(forEachParams);
		fileService.removeFile(userId, profileGrpId);
		
		userDAO.deleteUser(userId);
	}
	
	@Override
	public String removeProfileImg(String userId) {
		log.debug("▩▩▩ UserService .removeProfileImg() 호출.");
		
		UserVO user = userDAO.selectUserDetail(userId);
		String profileGrpId = user.getProfileGrpId();
		
		fileService.removeFile(userId, profileGrpId);
		userDAO.deleteProfileImg(userId);
		
		return "redirect:/cmmn/user/update?userId=" + userId;
	}
	
//	@Override
//	public List<UserVO> readUserListBySearch(String search) {
//		log.debug("▩▩▩ UserService .readUserListBySearch() 호출.");
//		
//		return userDAO.selectUserListBySearch(search);
//	}
//	
	@Override
	public List<UserVO> readUserList(SearchVO search) {
		log.debug("▩▩▩ UserService .readUserList() 호출.");
		
		return userDAO.selectUserList(search);
	}

	@Override
	public List<String> readUserRoles(String userId) {
		log.debug("▩▩▩ UserService .readUserRoles() 호출.");
		
		return userDAO.selectUserRoles(userId);
	}
}













