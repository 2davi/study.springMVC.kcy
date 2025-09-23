/**
 * 
 */
package kr.letech.study.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.board.service.BoardService;
import kr.letech.study.board.vo.CommentsVO;
import kr.letech.study.board.vo.PostsVO;
import kr.letech.study.cmmn.file.service.FileService;
import kr.letech.study.cmmn.sec.annotation.CurrentUser;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
import kr.letech.study.cmmn.utils.PercentDecoder;
import kr.letech.study.cmmn.vo.SearchVO;
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
 *  2025-09-16		KCY				최초 생성
 *  2025-09-17		KCY				URL 매핑 작업
 *  2025-09-19		KCY				게시판 CRUD 시작
 *  2025-09-22		KCY				단일게시판 CRUD 완성
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	private final FileService fileService;
	@SuppressWarnings("unused")
	private final MessageSource messageSource;
	private final String BOARD_CATE = "forum";
	
	@GetMapping("")
	public String boardCategory(Model model) {
		
		return "board/boardCategory.tiles";
	}
	
	
//	- (목록) : GET	/board/forum/list
//	- (검색) : GET	/board/forum/list?key=...&value=...
	@GetMapping("/{boardCate}/list")
	public String postList(Model model
			, @PathVariable("boardCate") String boardCd
			, @RequestParam(name="key", required=false, defaultValue="0") Integer keyword
			, @RequestParam(name="term", required=false, defaultValue="default") String term) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/list (BoardController) 연결.");
	
		/** 검색 파라미터 검증 */
		SearchVO search= PercentDecoder.searchParamValidate(keyword, term);
		
		/** 게시글 목록 조회 서비스 실행 */
		List<PostsVO> postList = boardService.readPostList(model, search);

		//첨부파일 수도 세보자.

		/** 모델&뷰레이어 반환 */
		model.addAttribute("postList", postList);
		return "board/postList.tiles";
	}
	 
//	- (상세) : GET	/board/forum/post
	@GetMapping("/{boardCate}/post")
	public String postDetail(Model model
			, @PathVariable("boardCate") String boardCd
			, @RequestParam(name="postId") String postId
			, @CurrentUser UserDetailsVO user) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/post (BoardController) 연결.");
		
	    /** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 게시글 상세 조회 서비스 실행 */
		boardService.readPostDetail(model, postId, username);
		
		/** 뷰레이어 반환 */
		return "board/postDetail.tiles";
	}
	
//	- (등록) : GET	/board/post/insert
	@GetMapping("/{boardCate}/post/insert")
	public String postInsert(Model model
			) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/post/insert (BoardController) 연결.");
		
		/** 뷰레이어 반환 */
		return "board/postInsert.tiles";
	}

//	- (수정) : GET	/board/post/update?boardId=...
	@GetMapping("/{boardCate}/post/update")
	public String postUpdate(Model model
			, @RequestParam("postId") String postId
			, @CurrentUser UserDetailsVO user
			) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/post/update (BoardController) 연결.");
		
	    /** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 게시글 수정 데이터 조회 서비스 실행 */
		boardService.readPostDetail(model, postId, username);
		
		/** 뷰레이어 반환 */
		return "board/postUpdate.tiles";
	}


//	- (등록) : POST	/board/post/insert
	@PostMapping(value="/post/insert", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public ResponseEntity<Map<String, Object>> postInsert(Model model
			, @ModelAttribute PostsVO post
			, @RequestParam(name="attachFiles", required=false) MultipartFile[] attachFiles
			, @RequestParam(name="lastModified", required=false) List<Long> lastModifiedList
			, @CurrentUser UserDetailsVO user
			) {
		log.debug("▩▩▩ URL: POST/board/post/insert (BoardController) 연결.");
	    log.debug("첨부파일 개수: {}", attachFiles != null ? attachFiles.length : 0);
		
	    /** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 게시글 등록 서비스 실행 */
		Map<String, Object> body = new HashMap<>();
		boardService.createPost(body, post, attachFiles, lastModifiedList, username);

		/** 비동기 응답 반환 */
		return ResponseEntity.ok(body);
	}
	
	
//	- (수정) : POST	/board/post/update
	@PostMapping(value="/post/update", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public ResponseEntity<Map<String, Object>> postUpdate(
			@ModelAttribute PostsVO post
			, @RequestParam(name="attachFiles", required=false) MultipartFile[] attachFiles
			, @RequestParam(name="deleteFileSeq", required=false) List<String> deleteFileSeqList
			, @RequestParam(name="lastModified", required=false) List<Long> lastModifiedList
			, @CurrentUser UserDetailsVO user) {
		log.debug("▩▩▩ URL: POST/board/post/update (BoardController) 연결.");
		log.debug("▩ ----- 0923_테스트: fileGrpId : {}", post.getAttachGrpId());
		/** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 게시글 수정 서비스 실행 */
		Map<String, Object> body = new HashMap<>();
		boardService.modifyPost(body, post, attachFiles, deleteFileSeqList, lastModifiedList, username);

		/** 비동기 응답 반환 */
		return ResponseEntity.ok(body);
	}
	
	
//	- (삭제) : POST	/board/post/delete
	@GetMapping("/post/delete")
	public String postDelete(
			@RequestParam("postId") String postId
			, @CurrentUser UserDetailsVO user
			) {
		log.debug("▩▩▩ URL: POST/board/post/delete (BoardController) 연결.");
		
		
		/** 로그인 정보 & 게시판 카테고리 조회 */
		String boardCate = BOARD_CATE;
		String username = user.getUsername();
		
		/** 게시글 삭제 서비스 실행 */
		boardService.removePost(username, postId);
		
		/** 비동기 응답 반환 */
		return "redirect:/board/"+boardCate+"/list";
	}


//	- (파일다운) : /board/attach/select
	@GetMapping("/attach/select")
	public void attachFileDownload(HttpServletResponse response
			, @RequestParam("fileGrpId") String fileGrpId
			, @RequestParam("fileSeq") String fileSeq
			) {
		log.debug("▩▩▩ URL: GET/board/attach/select (BoardController) 연결.");
		
		/** 파일 다운로드 서비스 실행 */
		fileService.downloadFile(fileGrpId, fileSeq, response);
	}
	
	
//	- (덧글조회) : POST /board/comment/select
	@GetMapping(value="/comment/select", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public ResponseEntity<Map<String, Object>> commentSelect(
			@RequestParam("postId") String postId
			, @CurrentUser UserDetailsVO user
			) {
		log.debug("▩▩▩ URL: GET/board/comment/select (BoardController) 연결.");
		
		/** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 덧글 조회 서비스 실행 */
		Map<String, Object> body = new HashMap<>();
		boardService.readComments(body, postId, username);
		
		/** 비동기 응답 반환 */
		return ResponseEntity.ok(body);
	}
	
//	- (덧글등록) : POST /board/comment/insert
	@PostMapping(value="/comment/insert", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public ResponseEntity<Map<String, Object>> commentInsert(
			@RequestBody CommentsVO comment
			, @CurrentUser UserDetailsVO user
			) {
		log.debug("▩▩▩ URL: POST/board/comment/insert (BoardController) 연결.");

		/** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 덧글 등록 서비스 실행 */
		Map<String, Object> body = new HashMap<>();
		boardService.createComment(body, comment, comment.getPostId(), username);
		
		/** 비동기 응답 반환 */
		return ResponseEntity.ok(body);
	}
	
//	- (덧글수정) : POST /board/comment/update
	@PostMapping(value="/comment/update", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public ResponseEntity<Map<String, Object>> commentUpdate(
			@RequestBody CommentsVO comment
			, @CurrentUser UserDetailsVO user) {
		log.debug("▩▩▩ URL: POST/board/comment/update (BoardController) 연결.");

		/** 로그인 정보 조회 */
		String username = user.getUsername();
		
		/** 덧글 수정 서비스 실행 */
		Map<String, Object> body = new HashMap<>();
		boardService.modifyComment(body, comment, username);
		
		/** 비동기 응답 반환 */
		return ResponseEntity.ok(body);
	}
	
//	- (덧글삭제) : POST /board/comment/delete
	@PostMapping(value="/comment/delete", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public ResponseEntity<Map<String, Object>> commentDelete(
			@RequestBody Map<String, String> payload
			, @CurrentUser UserDetailsVO user) {
		log.debug("▩▩▩ URL: POST/board/comment/delete (BoardController) 연결.");
		
		/** 로그인 정보 조회 */
		String username = user.getUsername();
		
		//---
		String cmtId = payload.get("cmtId");
		log.debug("▩ ----- 파라미터 검증 : CMT_ID : {}", cmtId);
		
		/** 덧글 삭제 서비스 실행 */
		Map<String, Object> body = new HashMap<>();
		boardService.removeComment(body, cmtId, username);
		
		/** 비동기 응답 반환 */
		return ResponseEntity.ok(body);
	}
	
}
