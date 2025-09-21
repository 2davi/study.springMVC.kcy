/**
 * 
 */
package kr.letech.study.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.board.vo.CommentsVO;
import kr.letech.study.board.vo.PostsVO;
import kr.letech.study.cmmn.vo.SearchVO;

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
 *  2025-09-17		KCY				최초 작업
 */
public interface BoardService {
	public List<PostsVO> readPostList(Model model, SearchVO search);
	public PostsVO readPostDetail(Model model, String postId);
	public void createPost(PostsVO post, String userId);
//	public String createPost(Model model, PostsVO post, MultipartFile[] attachFiles, String userId);
	public String createPost(Map<String, Object> body, PostsVO post, MultipartFile[] attachFiles, String userId);
	public String modifyPost(Map<String, Object> body, PostsVO post, MultipartFile[] attachFiles, List<String> deleteFileSeqList, String userId);
	public void removePost(String username, String postId);
	
	
	public void readComments(Map<String, Object> body, String postId);
	public void createComment(Map<String, Object> body, CommentsVO cmt, String postId, String username);
	public void modifyComment(Map<String, Object> body, CommentsVO cmt, String username);
	public void removeComment(Map<String, Object> body, String cmtId, String username);
}
