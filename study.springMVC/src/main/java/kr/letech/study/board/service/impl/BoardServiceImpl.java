/**
 * 
 */
package kr.letech.study.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.letech.study.board.dao.BoardDAO;
import kr.letech.study.board.service.BoardService;
import kr.letech.study.board.vo.CommentsVO;
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
 *  2025-09-22		KCY				조회/등록/삭제 완성
 *  2025-09-23		KCY				수정 기능 수정 중
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
	public PostsVO readPostDetail(Model model, String postId, String username) {
		log.debug("▩▩▩ BoardService .readPostDetail() 호출.");

		List<FilesVO> attachFileList = null;
		String jsonAttachList = null;
		ObjectMapper objectMapper = new ObjectMapper();

		log.debug("▩ ----- postId가 넘어오나요? : {}", postId);
		PostsVO post = boardDAO.selectPostDetail(postId);
		
		//---1. 상세조회 페이지에서 쓸 파일 리스트
		log.debug("▩ ----- 첨부파일 ID가 있나요? : {}", post);
		if(!post.getAttachGrpId().isBlank()) {
			attachFileList = fileService.readAttachFileList(post.getAttachGrpId());
		}
		
		//---2. 수정 페이지에서 쓸 json 변환 문자열
		try {
			jsonAttachList = objectMapper.writeValueAsString(attachFileList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("post", post);
		model.addAttribute("attachFileList", attachFileList);
		model.addAttribute("jsonAttachList", jsonAttachList);
		model.addAttribute("username", username);
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
	public String createPost(Map<String, Object> body
			, PostsVO post
			, MultipartFile[] attachFiles
			, List<Long> lastModifiedList
			, String userId) {
		log.debug("▩▩▩ BoardService .createPost() 호출.");

		List<FilesVO> fileVOList = null;
		
		//파일이 존재하는지 체크 후 첨부파일 처리
		if(attachFiles != null && attachFiles.length > 0) {
			log.debug("▩ ----- 첨부파일 존재함.");
			fileVOList = fileService.createFile(attachFiles, lastModifiedList, CMMN_CD_BOARD, post.getUserId(), post.getAttachGrpId());
			
			String attachGrpId = fileVOList.get(0).getFileGrpId();
			
			/**/
			if(attachFiles.length != fileVOList.size() || attachGrpId == null) {
				body.put("Msg", "첨부파일 처리 중 오류가 발생했습니다.");
				body.put("post", post);
				return "redirect:/board/"+BOARD_CATE+"/post/insert";
			}
			post.setAttachGrpId(attachGrpId);
		
			for(FilesVO fileVO: fileVOList) {
				log.debug("▩ ------------- ▩");
				log.debug("▩ ----- fileVO :: {}", fileVO.toString());
				
				
			}
		} else {
			log.debug("▩ ----- 첨부파일 없음.");
		}
		
		
		createPost(post, userId);
		body.put("boardCate", BOARD_CATE);
		body.put("postId", post.getPostId());
		return "redirect:/board/"+BOARD_CATE+"/post?postId=" + post.getPostId();
	}
	
	@Override @Transactional
	public String modifyPost(Map<String, Object> body
			, PostsVO post
			, MultipartFile[] attachFiles
			, List<String> deleteFileSeqList
			, List<Long> lastModifiedList
			, String userId) {
		log.debug("▩▩▩ BoardService .modifyPost() 호출.");
		
		log.debug("▩ ----- deleteFileSeqList: {}", deleteFileSeqList);
		
		if(deleteFileSeqList != null && !deleteFileSeqList.isEmpty()) {
	        log.debug("▩ ----- 삭제할 파일 존재: {}개", deleteFileSeqList.size());
	        fileService.removeFiles(userId, post.getAttachGrpId(), deleteFileSeqList);
	    }
		
	    if(attachFiles != null && attachFiles.length > 0) {
	        log.debug("▩ ----- 새 첨부파일 존재함.");
	        List<FilesVO> fileVOList = fileService.createFile(attachFiles, lastModifiedList, CMMN_CD_BOARD, userId, post.getAttachGrpId());

	        if(fileVOList == null || fileVOList.isEmpty()) {
	            body.put("Msg", "첨부파일 처리 중 오류가 발생했습니다.");
	        }
	    } else {
	        log.debug("▩ ----- 새 첨부파일 없음.");
	    }
		

		log.debug("▩▩▩ BoardService .modifyPost() 호출.");
		post.setUpdtId(userId);
		boardDAO.updatePost(post);
		log.debug("▩ =+=+=+=+=+=+ 수정 끝나고 되돌려주는 post 확인염 : {}", post);
		
		body.put("boardCate", BOARD_CATE);
	    body.put("postId", post.getPostId());
		return "redirect:/board/"+BOARD_CATE+"/post/"+ post.getPostId();
	}

	@Override
	public void removePost(String username, String postId) {
		log.debug("▩▩▩ BoardService .removePost() 호출.");

		if(username != null && postId != null) {
			log.debug("▩ ----- 파라미터 둘 모두 존재, 정상적.");
			
			PostsVO post = boardDAO.selectPostDetail(postId);
			
			if(post != null && username.equals(postId)) {
				log.debug("▩ ----- USERNAME == POST.USER_ID 본인 확인 완료");
				
				boardDAO.deletePost(postId);
			}
		}
	}

	
	
	@Override
	public void readComments(Map<String, Object> body, String postId, String username) {
		log.debug("▩▩▩ BoardService .readComments() 호출.");
		
		List<CommentsVO> cmtList = boardDAO.selectCommentList(postId);
		log.debug("▩ ----- 불러온 댓글: {} ", cmtList);
		for(CommentsVO cmt: cmtList) {
			log.debug("▩ ----- cmtPath : {}", cmt.getCmtPath());
		}
		body.put("cmtList" , cmtList);
	}

	@Override
	public void createComment(Map<String, Object> body, CommentsVO cmt, String postId, String username) {
		log.debug("▩▩▩ BoardService .createComment() 호출.");
		
		log.debug("▩ ----- POST_ID : {}, USERNAME : {}", postId, username);
		cmt.setPostId(postId);
		cmt.setUserId(username);
		cmt.setRgstId(username);
		cmt.setUpdtId(username);
		cmt.setHiddenYn("N");
		cmt.setBannedYn("N");
		
		Integer cmtOrder = boardDAO.selectNextCommentOrder(postId, cmt.getCmtParentId());
		cmt.setCmtOrder(cmtOrder);
		
		boardDAO.insertComment(cmt);
		log.debug("▩ ----- 등록된 댓글 : {}", cmt);
		
		body.put("cmt", cmt);
		body.put("username", username);
	}

	@Override
	public void modifyComment(Map<String, Object> body, CommentsVO cmt, String username) {
		log.debug("▩▩▩ BoardService .modifyComment() 호출.");
		
		cmt.setUpdtId(username);
		cmt.setHiddenYn("N");
		
		boardDAO.updateComment(cmt);
		log.debug("▩ ----- 수정된 댓글 : {}", cmt);
		
		body.put("cmtId", cmt.getCmtId());
	}

	@Override
	public void removeComment(Map<String, Object> body, String cmtId, String username) {
		log.debug("▩▩▩ BoardService .removeComment() 호출.");
		
		boardDAO.deleteComment(username, cmtId);
		log.debug("▩ ----- 삭제된 댓글 : {}", cmtId);
		
		body.put("cmtId", cmtId);
	}
}
