/**
 * 
 */
package kr.letech.study.board.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
	public String createPost(Model model, PostsVO post, MultipartFile[] attachFiles, String userId);
}
