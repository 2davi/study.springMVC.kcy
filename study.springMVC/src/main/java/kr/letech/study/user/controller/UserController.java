/**
 * 
 */
package kr.letech.study.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.code.service.CommonCodeService;
import kr.letech.study.cmmn.code.vo.CommonCodeVO;
import kr.letech.study.cmmn.file.service.FileService;
import kr.letech.study.cmmn.sec.annotation.CurrentUser;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
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
 *  2025-09-15		KCY				Create, Modify, Remove 핸들러 추가
 *  								Search 기능 Read(/list) 핸들러 수정
 *  2025-09-16		KCY				Spring Security 적용,
 *  								+ 사용자 등록/수정 시 권한 테이블 갱신
 *  								+ 사용자 등록 시 비밀번호 암호화
 *  2025-09-29		KCY				REST API 전환 작업 착수
 *  								
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cmmn/user")
public class UserController {
	private final UserService userService;
	private final CommonCodeService commonCodeService;
	private final FileService fileService;

	@GetMapping("/list")
	public String userList(Model model
			, HttpServletRequest request
			, @RequestParam(name="key", required=false, defaultValue="0") Integer keyword
			, @RequestParam(name="term", required=false, defaultValue="default") String term
			, @CurrentUser UserDetailsVO loginUser) {
		log.debug("▩▩▩ URL: GET/cmmn/user/list (UserController) 연결.");

		userService.readUserList(model, loginUser, keyword, term);
		return "cmmn/user/userList.tiles";
	}
	
	
	@GetMapping("/detail")
	public String userDetail(Model model
			, @RequestParam("userId") String userId
			, @CurrentUser UserDetailsVO loginUser) {
		log.debug("▩▩▩ URL: GET/cmmn/user/detail (UserController) 연결.");
		
		userService.readUserDetail(model, loginUser, userId);
		return "cmmn/user/userDetail.tiles";
	}
	
	
	@GetMapping("/insert")
	public String userInsert(Model model
			, @CurrentUser UserDetailsVO loginUser) {
		log.debug("▩▩▩ URL: GET/cmmn/user/insert (UserController) 연결.");
		
		List<String> cmmnCodeParams = List.of("AA");
		List<CommonCodeVO> cmmnCodeList = commonCodeService.readCommonCodeList(cmmnCodeParams);

		//0918_등록된프로필사진 불러오기
		String userProfileImgSrc = fileService.readUserProfileImgSrc(null);
		
		model.addAttribute("cmmnCodeList", cmmnCodeList);
		//model.addAttribute("username", user.getUsername());
		return "cmmn/user/userInsert.tiles";
	}
	
	@GetMapping("/update")
	public String userInsert(Model model
			, @RequestParam("userId") String userId
			, @CurrentUser UserDetailsVO loginUser) {
		log.debug("▩▩▩ URL: GET/cmmn/user/update (UserController) 연결.");

		log.info("▩ userId = {}", userId);
		UserVO user = userService.readUserDetail(model, loginUser, userId);
		List<String> userRoles = userService.readUserRoles(userId);
		List<String> cmmnCodeParams = List.of("AA");
		List<CommonCodeVO> cmmnCodeList = commonCodeService.readCommonCodeList(cmmnCodeParams);
		
		//0918_등록된프로필사진 불러오기
		String userProfileImgSrc = fileService.readUserProfileImgSrc(user.getProfileGrpId());
		
		model.addAttribute("user", user);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("cmmnCodeList", cmmnCodeList);
		model.addAttribute("userProfileImgSrc", userProfileImgSrc);
		model.addAttribute("username", loginUser.getUsername());
		return "cmmn/user/userUpdate.tiles";
	}
	
	@PostMapping("/insert")
	public String userInsert(Model model
			, /* @RequestAttribute("insertUser") */UserVO user
			, @RequestParam(name="userRoles", required=false) List<String> userRoles
			, @RequestParam(name="userProfileImg", required=false) MultipartFile multipart
			, @CurrentUser UserDetailsVO loginUser) {
		log.debug("▩▩▩ URL: POST/cmmn/user/insert (UserController) 연결.");
		

		String logicalViewName = userService.createUser(model, loginUser, user, userRoles, multipart);		
		return logicalViewName;
	}
	
	@PostMapping("/update")
	public String userUpdate(Model model
			, /* @RequestAttribute("user") */ UserVO user
			, @RequestParam(name="userRoles", required=false) List<String> userRoles
			, @RequestParam(name="userProfileImg", required=false) MultipartFile multipart
			, @CurrentUser UserDetailsVO loginUser) {
		log.debug("▩▩▩ URL: POST/cmmn/user/update (UserController) 연결.");

		String logicalViewName = userService.modifyUser(model, loginUser, user, userRoles, multipart);
		return logicalViewName;
	}
	
	@GetMapping("/delete")
	public String userDelete(Model model
			, @RequestParam("userId") String userId) {
		log.debug("▩▩▩ URL: GET/cmmn/user/delete (UserController) 연결.");

		userService.removeUser(userId);
		
		return "redirect:/cmmn/user/list";
	}

	@GetMapping("/delete/img")
	public String profileImgDelete(Model model
			, @RequestParam("userId") String userId) {
		log.debug("▩▩▩ URL: GET/cmmn/user/delete/img (UserController) 연결.");
		
		String logicalViewName = userService.removeProfileImg(userId);
		
		return logicalViewName;
	}
	
	@GetMapping("/profile-img/select")
	public void downloadProfileImg(HttpServletResponse response
			,@RequestParam("profileGrpId") String profileGrpId) {
		log.debug("▩▩▩ URL: GET/cmmn/user/detail/download (UserController) 연결.");
		
		fileService.downloadFile(profileGrpId, "1", response);
	}
	

}


	
























