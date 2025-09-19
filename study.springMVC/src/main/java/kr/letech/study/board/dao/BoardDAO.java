/**
 * 
 */
package kr.letech.study.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
 *  2025-09-19		KCY				최초 작업
 */
@Mapper
public interface BoardDAO {
	
	public List<PostsVO> selectPostList(SearchVO search);
	public PostsVO selectPostDetail(String postId);
	public int insertPost(PostsVO post);

}
