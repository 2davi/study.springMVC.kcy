/**
 * 
 */
package kr.letech.study.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 *  2025-09-19		KCY				최초 작업
 */
@Mapper
public interface BoardDAO {
	
	public List<PostsVO> selectPostList(SearchVO search);
	public PostsVO selectPostDetail(String postId);
	public Integer insertPost(PostsVO post);
	public Integer updatePost(PostsVO post);
	public Integer deletePost(String postId);
	
	
	public List<CommentsVO> selectCommentList(String postId);
	public CommentsVO selectComment(String cmtId);
	public Integer insertComment(CommentsVO cmt);
	public Integer updateComment(CommentsVO cmt);
	public Integer deleteComment(@Param("userId") String userId, @Param("cmtId") String cmtId);
	
	public Integer selectNextCommentOrder(@Param("postId") String postId, @Param("cmtParentId") String cmtParentId);
}
