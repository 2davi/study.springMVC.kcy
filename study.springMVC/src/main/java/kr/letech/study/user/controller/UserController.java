/**
 * 
 */
package kr.letech.study.user.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import kr.letech.study.cmmn.utils.FileStorageUtils;
import kr.letech.study.cmmn.utils.PercentDecoder;
import kr.letech.study.cmmn.vo.SearchVO;
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
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cmmn/user")
public class UserController {
	
	private final UserService userService;
	private final CommonCodeService commonCodeService;
	private final FileService fileService;
//	private final MessageSource messageSource;
//	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final BCryptPasswordEncoder passwordEncoder;
	private final FileStorageUtils fileUtils;

	@GetMapping("/list")
	public String userList(Model model
			, HttpServletRequest request
			, @RequestParam(name="key", required=false, defaultValue="0") Integer keyword
			, @RequestParam(name="term", required=false, defaultValue="default") String term) {
		log.debug("▩▩▩ URL: GET/cmmn/user/list (UserController) 연결.");

		List<UserVO> userList = null;
		
		/** 검색 파라미터 검증 */
		//--- 파라미터 초기화
		if(keyword != 0 && keyword != 1 && keyword != 2) {
			keyword = 0;
			term = "default";
			
		//--- 파라미터 디코딩
		} else {
			term = PercentDecoder.decodeURLSafe(term);
		}
		log.debug("▩ SEARCH-BOX: KEYWORD = \"{}\", TERM = \"{}\"", keyword, term);
		SearchVO search = new SearchVO(keyword, term);
		

		userList = userService.readUserList(search);
		
		model.addAttribute("userList", userList);
		return "cmmn/user/userList.tiles";
	}
	
	
	@GetMapping("/detail")
	public String userDetail(Model model
			, @RequestParam("userId") String userId) {
		log.debug("▩▩▩ URL: GET/cmmn/user/detail (UserController) 연결.");
		
		UserVO user = userService.readUserDetail(userId);
		List<String> userRoles = userService.readUserRoles(userId);
		List<String> cmmnCodeParams = List.of("AA");
		List<CommonCodeVO> cmmnCodeList = commonCodeService.readCommonCodeList(cmmnCodeParams);
		
		//0918_등록된프로필사진 불러오기
		String userProfileImgSrc = fileService.readUserProfileImgSrc(user.getProfileGrpId());
		
		
		model.addAttribute("user", user);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("cmmnCodeList", cmmnCodeList);
		model.addAttribute("userProfileImgSrc", userProfileImgSrc);
		return "cmmn/user/userDetail.tiles";
	}
	
	@GetMapping("/insert")
	public String userInsert(Model model) {
		log.debug("▩▩▩ URL: GET/cmmn/user/insert (UserController) 연결.");

		List<String> cmmnCodeParams = List.of("AA");
		List<CommonCodeVO> cmmnCodeList = commonCodeService.readCommonCodeList(cmmnCodeParams);

		//0918_등록된프로필사진 불러오기
		String userProfileImgSrc = fileService.readUserProfileImgSrc(null);
		
		model.addAttribute("cmmnCodeList", cmmnCodeList);
		return "cmmn/user/userInsert.tiles";
	}
	
	@GetMapping("/update")
	public String userInsert(Model model
			, @RequestParam("userId") String userId ) {
		log.debug("▩▩▩ URL: GET/cmmn/user/update (UserController) 연결.");

		log.info("▩ userId = {}", userId);
		UserVO user = userService.readUserDetail(userId);
		List<String> userRoles = userService.readUserRoles(userId);
		List<String> cmmnCodeParams = List.of("AA");
		List<CommonCodeVO> cmmnCodeList = commonCodeService.readCommonCodeList(cmmnCodeParams);
		
		//0918_등록된프로필사진 불러오기
		String userProfileImgSrc = fileService.readUserProfileImgSrc(user.getProfileGrpId());
		
		model.addAttribute("user", user);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("cmmnCodeList", cmmnCodeList);
		model.addAttribute("userProfileImgSrc", userProfileImgSrc);
		return "cmmn/user/userUpdate.tiles";
	}
	
	/** C UD 기능 */
	@PostMapping("/insert")
	public String userInsert(Model model
			, /* @RequestAttribute("insertUser") */UserVO user
			, @RequestParam(name="userRoles", required=false) List<String> userRoles
			, @RequestParam(name="userProfileImg", required=false) MultipartFile multipart) {
		log.debug("▩▩▩ URL: POST/cmmn/user/insert (UserController) 연결.");
		
		log.info("▩ FORM-USER : {}", user.toString());
		Date now = new Date();
		user.setRgstDt(now);
		
		String logicalViewName = userService.createUser(model, user, userRoles, multipart);
		
//		return "redirect:/cmmn/user/detail?userId=" + user.getUserId(); <--시큐리티 처리 후 인가 처리당함
		return logicalViewName;
	}
	
	@PostMapping("/update")
	public String userUpdate(Model model
			, /* @RequestAttribute("user") */ UserVO user
			, @RequestParam(name="userRoles", required=false) List<String> userRoles
			, @RequestParam(name="userProfileImg", required=false) MultipartFile multipart) {
		log.debug("▩▩▩ URL: POST/cmmn/user/update (UserController) 연결.");

		log.info("▩ MULTIPART : {}", multipart.getOriginalFilename());
		
		Date now = new Date();
		user.setUpdtDt(now);
		
		String logicalViewName =userService.modifyUser(model, user, userRoles, multipart);
		
		//return "redirect:/cmmn/user/detail?userId=" + user.getUserId();
		//위처럼 작성하면 아래처럼 url 찍힘
		//http://localhost:8080/cmmn/user/detail?userId=test02,test02 
		String userId = user.getUserId();
		log.info("▩ USER_ID : {}", userId);
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


	
























