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
public class CommentsVO extends BaseVO implements Serializable {
	/** Serialization Number */
	private static final long serialVersionUID = -664376120452247423L;
	
	/** Fields */
	private String cmtId;
	private String postId;
	private String userId;
	private String cmtContent;
	private String cmtParentId;
	private Integer cmtDepth;
	private Integer cmtOrder;
	private String hiddenYn;
	private String bannedYn;
	
	private String cmtPath;
	
	
	/** Constructors
	 * 
	 * @param cmtId
	 * @param postId
	 * @param userId
	 * @param cmtContent
	 * @param cmtParentId
	 * @param cmtDepth
	 * @param cmtOrder
	 * @param hiddenYn
	 * @param bannedYn
	 */
	public CommentsVO() {}
	public CommentsVO(String postId, String userId, String cmtContent, String cmtParentId,
			Integer cmtDepth, Integer cmtOrder) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.cmtContent = cmtContent;
		this.cmtParentId = cmtParentId;
		this.cmtDepth = cmtDepth;
		this.cmtOrder = cmtOrder;
	}
	public CommentsVO(String cmtId, String postId, String userId, String cmtContent, String cmtParentId,
			Integer cmtDepth, Integer cmtOrder, String hiddenYn, String bannedYn) {
		super();
		this.cmtId = cmtId;
		this.postId = postId;
		this.userId = userId;
		this.cmtContent = cmtContent;
		this.cmtParentId = cmtParentId;
		this.cmtDepth = cmtDepth;
		this.cmtOrder = cmtOrder;
		this.hiddenYn = hiddenYn;
		this.bannedYn = bannedYn;
	}
	
	
	/** Getter&Setter */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCmtId() {
		return cmtId;
	}
	public String getPostId() {
		return postId;
	}
	public String getUserId() {
		return userId;
	}
	public String getCmtContent() {
		return cmtContent;
	}
	public String getCmtParentId() {
		return cmtParentId;
	}
	public Integer getCmtDepth() {
		return cmtDepth;
	}
	public Integer getCmtOrder() {
		return cmtOrder;
	}
	public String getHiddenYn() {
		return hiddenYn;
	}
	public String getBannedYn() {
		return bannedYn;
	}
	public String getCmtPath() {
		return cmtPath;
	}
	public void setCmtId(String cmtId) {
		this.cmtId = cmtId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	public void setCmtParentId(String cmtParentId) {
		this.cmtParentId = cmtParentId;
	}
	public void setCmtDepth(Integer cmtDepth) {
		this.cmtDepth = cmtDepth;
	}
	public void setCmtOrder(Integer cmtOrder) {
		this.cmtOrder = cmtOrder;
	}
	public void setHiddenYn(String hiddenYn) {
		this.hiddenYn = hiddenYn;
	}
	public void setBannedYn(String bannedYn) {
		this.bannedYn = bannedYn;
	}
	public void setCmtPath(String cmtPath) {
		this.cmtPath = cmtPath;
	}
	
	
	/** HashCode&Equals&toString */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cmtId == null) ? 0 : cmtId.hashCode());
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
		CommentsVO other = (CommentsVO) obj;
		if (cmtId == null) {
			if (other.cmtId != null)
				return false;
		} else if (!cmtId.equals(other.cmtId))
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
		return "CommentsVO [cmtId=" + cmtId + ", postId=" + postId + ", userId=" + userId + ", cmtParentId="
				+ cmtParentId + ", cmtDepth=" + cmtDepth + ", cmtOrder=" + cmtOrder + ", cmtPath=" + cmtPath
				+ ", hiddenYn=" + hiddenYn + ", bannedYn=" + bannedYn + "]";
	}
}
