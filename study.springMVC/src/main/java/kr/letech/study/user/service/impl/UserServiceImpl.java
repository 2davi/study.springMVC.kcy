/**
 * 
 */
package kr.letech.study.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.code.service.CommonCodeService;
import kr.letech.study.cmmn.code.vo.CommonCodeVO;
import kr.letech.study.cmmn.file.service.FileService;
import kr.letech.study.cmmn.file.vo.FilesVO;
import kr.letech.study.cmmn.restTemplate.apiClient.CommonCodeApiClient;
import kr.letech.study.cmmn.restTemplate.apiClient.UserApiClient;
import kr.letech.study.cmmn.restTemplate.apiClient.UserRoleApiClient;
import kr.letech.study.cmmn.restTemplate.envelope.Envelope;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
import kr.letech.study.cmmn.utils.PercentDecoder;
import kr.letech.study.cmmn.vo.UserRoleVO;
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
 *  2025-09-29		KCY				REST API 전환 작업
 */
@Service @Slf4j @RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserDAO userDAO;
	private final FileService fileService;	
	private final BCryptPasswordEncoder passwordEncoder;
	@Qualifier("userApiClient")
	private final UserApiClient userApi;
	private final CommonCodeService commonCodeService;
	private final CommonCodeApiClient cmmnCodeApi;
	private final UserRoleApiClient userRoleApi;
	
	@Override
	public void readUserList(Model model, UserDetailsVO loginUser, Integer keyword, String term) {
		log.debug("▩▩▩ UserService .readUserList() 호출.");
		
		/** 검색 파라미터 검증 */
		log.debug("▩ SEARCH-BOX: KEYWORD = \"{}\", TERM = \"{}\"", keyword, term);
		if(keyword != 0 && keyword != 1 && keyword != 2) {
			keyword = 0;
			term = "default";
		} else term = PercentDecoder.decodeURLSafe(term);
		
		Envelope<List<UserVO>> response =  userApi.readUserList(keyword, term);
		model.addAttribute("username", loginUser.getUsername());
		model.addAttribute("userList", response.getData());
		return;
	}
	
	@Override @Transactional
	public UserVO readUserDetail(Model model, UserDetailsVO loginUser, String userId) {
		log.debug("▩▩▩ UserService .readUserDetail() 호출.");

		//0912_사용자정보 불러오기
//		UserVO user = userDAO.selectUserDetail(userId);
		UserVO user = userApi.readUserDetail(userId).getData();
		
		//0915_사용자권한 불러오기
//		List<String> userRoles = userDAO.selectUserRoles(userId);
		List<UserRoleVO> userRoles = userRoleApi.readRolesOfUser(userId).getData();
		
		//0915_공통코드 불러오기
//		List<String> cmmnCodeParams = List.of("AA");
//		List<CommonCodeVO> cmmnCodeList = commonCodeService.readCommonCodeList(cmmnCodeParams);
		
		String cmmnGrpCd = "AA";
		List<CommonCodeVO> cmmnCodeList = cmmnCodeApi.readCmmnCdList(cmmnGrpCd).getData();
		
		//0918_등록된프로필사진 불러오기
		String userProfileImgSrc = fileService.readUserProfileImgSrc(user.getProfileGrpId());
		
		//0922_로그인한 회원 아이디 불러오기
		String username = loginUser.getUsername();
		
		log.debug("▩ ----- MODEL 세팅작업 전 값들 확인");
		log.debug("▩ ----- userRoles : {}", userRoles);
		log.debug("▩ ----- cmmnCodeList : {}", cmmnCodeList);
		
		model.addAttribute("user", user);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("cmmnCodeList", cmmnCodeList);
		model.addAttribute("userProfileImgSrc", userProfileImgSrc);
		model.addAttribute("username", loginUser.getUsername());
		
		return user; //<--GET userDetail, POST updateUser
	}
	
	@Override @Transactional
	public String createUser(Model model, UserDetailsVO loginUser, UserVO user, List<String> userRoles, MultipartFile multipart) {
		log.debug("▩▩▩ UserService .createUser() 호출.");
		
		/** 프로필 첨부기능. */
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
		
		/** 사용자 등록기능. */
		// 1.사용자정보 USER 등록
		log.info("▩ USER insert 시작함");
		user = userApi.createUser(user).getData();
		
		// 2.사용자권한정보 USER_ROLE 등록
		log.info("▩ USER_ROLE insert 시작함");
		userRoleApi.mergeUserRoles(user.getUserId(), userRoles, null);

		return "redirect:/";
	}

	@Override
	@Transactional
	public String modifyUser(Model model, UserDetailsVO loginUser, UserVO user, List<String> userRoles, MultipartFile multipart) {
		log.debug("▩▩▩ UserService .modifyUser() 호출.");
		
		
		/** 프로필 첨부기능. */
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
		
		
		/** 사용자 등록기능. */
		//Security: 암호화된 비밀번호 저장
		user.setUserPw(passwordEncoder.encode(user.getUserPw()));
		
//		userDAO.updateUser(user);
//		
//		Map<String, Object> forEachParams = new HashMap<>();
//		forEachParams.put("userId", user.getUserId());
//		forEachParams.put("rgstId", user.getRgstId());
//		forEachParams.put("updtId", user.getUpdtId());
//		forEachParams.put("userRoles", userRoles);
//		userDAO.mergeUserRole(forEachParams);
//		userDAO.deleteUserRole(forEachParams);
		userRoleApi.mergeUserRoles(userId, userRoles, userId);
		
		/*
		 * Query 안에서 IN() 절이나 foreach 문법을 쓸 일이 없다.
		 * delete-insert는 보통 물리삭제의 경우에 
		 */
		
		model.addAttribute("username", loginUser.getUsername());
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



	@Override
	public List<String> readUserRoles(String userId) {
		log.debug("▩▩▩ UserService .readUserRoles() 호출.");
		
		return userDAO.selectUserRoles(userId);
	}
}













