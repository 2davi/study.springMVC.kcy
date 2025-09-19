/**
 * 
 */
package kr.letech.study.board.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.board.service.BoardService;
import kr.letech.study.board.vo.PostsVO;
import kr.letech.study.cmmn.sec.annotation.CurrentUser;
import kr.letech.study.cmmn.sec.vo.UserDetailsVO;
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
 *  2025-09-19		KCY				
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	private final MessageSource messageSource;
	private final String BOARD_CATE = "forum";
	
	@GetMapping("")
	public String boardCategory(Model model) {
		
		return "board/boardCategory.tiles";
	}
	
	
//	- (목록) : GET	/board/forum/list
//	- (검색) : GET	/board/forum/list?key=...&value=...
	@SuppressWarnings("unchecked")
	@GetMapping("/{boardCate}/list")
	public String postList(Model model
			, @PathVariable("boardCate") String boardCd
			, @RequestParam(name="key", required=false, defaultValue="0") Integer keyword
			, @RequestParam(name="term", required=false, defaultValue="default") String term) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/list (BoardController) 연결.");
	
		SearchVO search = new SearchVO(keyword, term);
		List<PostsVO> postList = boardService.readPostList(model, search);

		//첨부파일 수도 세보자.

		model.addAttribute("postList", postList);
		return "board/postList.tiles";
	}
	 
//	- (상세) : GET	/board/forum/post
	@GetMapping("/{boardCate}/post")
	public String postDetail(Model model
			, @PathVariable("boardCate") String boardCd
			, @RequestParam(name="postId") String postId) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/post (BoardController) 연결.");
		
		PostsVO post = boardService.readPostDetail(model, postId);
		
		
		return "board/postDetail.tiles";
	}
	
//	- (등록) : GET	/board/post/insert
	@GetMapping("/{boardCate}/post/insert")
	public String postInsert(Model model
			) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/post/insert (BoardController) 연결.");
		
		
		return "board/postInsert.tiles";
	}

//	- (수정) : GET	/board/post/update?boardId=...
	@GetMapping("/{boardCate}/post/update")
	public String postUpdate(Model model
			) {
		log.debug("▩▩▩ URL: GET/board/{boardCate}/post/update (BoardController) 연결.");
		
		
		return "board/postUpdate.tiles";
	}
	
	
//	- (등록) : POST	/board/post/insert
	@PostMapping("/post/insert")
	public String postInsert(Model model
			, @ModelAttribute PostsVO post
			, @RequestParam(name="attachFiles", required=false) MultipartFile[] attachFiles
			, @CurrentUser UserDetailsVO user
			) {
		log.debug("▩▩▩ URL: POST/board/post/insert (BoardController) 연결.");
	    log.debug("첨부파일 개수: {}", attachFiles != null ? attachFiles.length : 0);
		
		String userId = user.getUsername();
//		log.debug("▩▩▩ LOG-IN 사용자: {}, - {}", userId, user.getUserVO().getUserNm());
		
		String boardCate = BOARD_CATE;
		
		String logicalViewName = boardService.createPost(model, post, attachFiles, userId);
		
		String postId = post.getPostId();
		return logicalViewName;
	}
	
	
//	- (수정) : POST	/board/post/update
	@PostMapping("/post/update")
	public String postUpdate(
			@RequestParam("postId") String postId
			) {
		log.debug("▩▩▩ URL: POST/board/post/update (BoardController) 연결.");
		
		String boardCate = BOARD_CATE;
		
		

		return "redirect:/board/"+boardCate+"/post/"+postId;
	}
	
	
//	- (삭제) : POST	/board/post/delete
	@PostMapping("/post/delete")
	public String postDelete() {
		log.debug("▩▩▩ URL: POST/board/post/delete (BoardController) 연결.");
		
		
		String boardCate = BOARD_CATE;
		return "redirect:/board/"+boardCate+"/list";
	}

//	- (덧글조회) : POST /board/comment/select
	@PostMapping("/comment/select")
	public /*ResponseEntity<?>*/void commentSelect() {
		log.debug("▩▩▩ URL: POST/board/comment/select (BoardController) 연결.");
		
		
	}
	
//	- (덧글등록) : POST /board/comment/insert
	@PostMapping("/comment/insert")
	public void commentInsert() {
		log.debug("▩▩▩ URL: POST/board/comment/insert (BoardController) 연결.");
		
	}
	
//	- (덧글수정) : POST /board/comment/update
	@PostMapping("/comment/update")
	public void commentUpdate() {
		log.debug("▩▩▩ URL: POST/board/comment/update (BoardController) 연결.");
		
	}
	
//	- (덧글삭제) : POST /board/comment/delete
	@PostMapping("/comment/delete")
	public void commentDelete() {
		log.debug("▩▩▩ URL: POST/board/comment/delete (BoardController) 연결.");
		
		
	}
	
//	- (파일다운) : /board/attach/select
	public void attachFileDownload(
			@RequestParam("fileGrpId") String fileGrpId,
			@RequestParam("fileSeq") Integer fileSeq
			) {
		
	}
}
