/**
 * 
 */
package kr.letech.study.board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.board.dao.BoardDAO;
import kr.letech.study.board.service.BoardService;
import kr.letech.study.board.vo.PostsVO;
import kr.letech.study.cmmn.file.service.FileService;
import kr.letech.study.cmmn.file.vo.FilesVO;
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
 *  2025-09-19		KCY				최초 작업
 */
@Service @Slf4j @RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardDAO boardDAO;
	private final FileService fileService;
	private final String CMMN_CD_BOARD = "AF020";
	private final String BOARD_CATE = "forum";
	
	@Override
	public List<PostsVO> readPostList(Model model, SearchVO search) {
		log.debug("▩▩▩ BoardService .readPostList() 호출.");

		return boardDAO.selectPostList(search);
	}
	
	@Override @Transactional
	public PostsVO readPostDetail(Model model, String postId) {
		log.debug("▩▩▩ BoardService .readPostDetail() 호출.");
		PostsVO post = null;
		
		post = boardDAO.selectPostDetail(postId);
		List<FilesVO> attachFileList = fileService.readAttachFileList(post.getAttachGrpId());
		
		model.addAttribute("post", post);
		model.addAttribute("attachFileList", attachFileList);
		model.addAttribute("isOk", "이게 되나??");//된다
		return post;
	}
	
	
	@Override
	public void createPost(PostsVO post, String userId) {
		log.debug("▩▩▩ BoardService .createPost() 호출.");
		
		post.setUserId(userId);
		if(post.getHiddenYn() == null) {
			post.setHiddenYn("N");
		}
		if(post.getCmtYn() == null) {
			post.setCmtYn("N");
		}
		post.setBannedYn("N");
		post.setRgstId(userId);
		post.setUpdtId(userId);
		
		log.debug("▩ ----- PostVO : {}", post.toString());
		boardDAO.insertPost(post);
	}
	
	@Override @Transactional
	public String createPost(Model model
			, PostsVO post
			, MultipartFile[] attachFiles
			, String userId) {
		log.debug("▩▩▩ BoardService .createPost() 호출.");

		List<FilesVO> fileVOList = null;
		
		//파일이 존재하는지 체크 후 첨부파일 처리
		if(attachFiles != null && attachFiles.length > 0) {
			log.debug("▩ ----- 첨부파일 존재함.");
			fileVOList = fileService.createFile(attachFiles, CMMN_CD_BOARD, post.getUserId());
			
			String attachGrpId = fileVOList.get(0).getFileGrpId();
			
			if(attachFiles.length != fileVOList.size() || attachGrpId == null) {
				model.addAttribute("Msg", "첨부파일 처리 중 오류가 발생했습니다.");
				model.addAttribute("post", post);
				return "redirect:/board/"+BOARD_CATE+"/post/insert";
			}
			post.setAttachGrpId(attachGrpId);
		
		} else {
			log.debug("▩ ----- 첨부파일 없음.");
		}
		
		createPost(post, userId);
		return "redirect:/board/"+BOARD_CATE+"/post?postId=" + post.getPostId();
	}
}
