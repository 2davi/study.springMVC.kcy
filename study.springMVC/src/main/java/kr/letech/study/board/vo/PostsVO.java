/**
 * 
 */
package kr.letech.study.board.vo;

import java.io.Serializable;

import kr.letech.study.sample.BaseVO;

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
 */
public class PostsVO extends BaseVO implements Serializable {
	/** Serialization Number */
	private static final long serialVersionUID = 7564887017410481676L;

	/** Fields */
	private String postId;
	private String userId;
	private String boardCd;
	private String postTitle;
	private String postContent;
	private String postParentId;
	private String hiddenYn;
	private String cmtYn;
	private String bannedYn;
	private String attachGrpId;
	
	
	/** Constructors
	 * 
	 * @param postId
	 * @param userId
	 * @param boardCd
	 * @param postTitle
	 * @param postContent
	 * @param postParentId
	 * @param hiddenYn
	 * @param cmtYn
	 * @param bannedYn
	 */
	public PostsVO() {};
	public PostsVO(String userId, String boardCd, String postTitle, String postContent) {
		super();
		this.userId = userId;
		this.boardCd = boardCd;
		this.postTitle = postTitle;
		this.postContent = postContent;
	}
	public PostsVO(String postId, String userId, String boardCd, String postTitle, String postContent,
			String postParentId, String hiddenYn, String cmtYn, String bannedYn, String attachGrpId) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.boardCd = boardCd;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postParentId = postParentId;
		this.hiddenYn = hiddenYn;
		this.cmtYn = cmtYn;
		this.bannedYn = bannedYn;
		this.attachGrpId = attachGrpId;
	}
	

	/** Getter&Setter */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPostId() {
		return postId;
	}
	public String getUserId() {
		return userId;
	}
	public String getBoardCd() {
		return boardCd;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public String getPostParentId() {
		return postParentId;
	}
	public String getHiddenYn() {
		return hiddenYn;
	}
	public String getCmtYn() {
		return cmtYn;
	}
	public String getBannedYn() {
		return bannedYn;
	}
	public String getAttachGrpId() {
		return attachGrpId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setBoardCd(String boardCd) {
		this.boardCd = boardCd;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public void setPostParentId(String postParentId) {
		this.postParentId = postParentId;
	}
	public void setHiddenYn(String hiddenYn) {
		this.hiddenYn = hiddenYn;
	}
	public void setCmtYn(String cmtYn) {
		this.cmtYn = cmtYn;
	}
	public void setBannedYn(String bannedYn) {
		this.bannedYn = bannedYn;
	}
	public void setAttachGrpId(String attachGrpId) {
		this.attachGrpId = attachGrpId;
	}
	

	/** HashCode&Equals&toString */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((boardCd == null) ? 0 : boardCd.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostsVO other = (PostsVO) obj;
		if (boardCd == null) {
			if (other.boardCd != null)
				return false;
		} else if (!boardCd.equals(other.boardCd))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PostsVO [postId=" + postId + ", userId=" + userId + ", boardCd=" + boardCd + ", postTitle=" + postTitle
				+ ", postParentId=" + postParentId
				+ ", hiddenYn=" + hiddenYn + ", cmtYn=" + cmtYn + ", bannedYn=" + bannedYn + ", attachGrpId=" + attachGrpId +"]";
	}
}
