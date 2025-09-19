/**
 * 
 */
package kr.letech.study.cmmn.vo;

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
 *  2025-09-12		KCY				최초 생성
 */
public class UserRoleVO extends BaseVO implements Serializable{
	private static final long serialVersionUID = 4654038696194157003L;
	
	private String userRole;
	private String userId;
	
	/**
	 * @param userRole
	 * @param userId
	 */
	public UserRoleVO() {}
	public UserRoleVO(String userRole, String userId) {
		super();
		this.userRole = userRole;
		this.userId = userId;
	}
	
	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoleVO other = (UserRoleVO) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userRole == null) {
			if (other.userRole != null)
				return false;
		} else if (!userRole.equals(other.userRole))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserRoleVO [userRole=" + userRole + ", userId=" + userId + "]";
	}
}
