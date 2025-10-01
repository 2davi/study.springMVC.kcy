/**
 * 
 */
package kr.letech.study.user.vo;

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
public class UserVO extends BaseVO implements Serializable{
	private static final long serialVersionUID = 4234420295018119435L;
	
	private String userId;
	private String userPw;
	private String userNm;
	private Integer regno1;
	private Integer regno2;
	private String profileGrpId;
	/**
	 * @param userId
	 * @param userPw
	 * @param userNm
	 * @param regno1
	 * @param regno2
	 */
	public UserVO() {}
	public UserVO(String userId, String userPw, String userNm, Integer regno1, Integer regno2, String profileGrpId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userNm = userNm;
		this.regno1 = regno1;
		this.regno2 = regno2;
		this.profileGrpId = profileGrpId;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @return the userPw
	 */
	public String getUserPw() {
		return userPw;
	}
	/**
	 * @return the userNm
	 */
	public String getUserNm() {
		return userNm;
	}
	/**
	 * @return the regno1
	 */
	public Integer getRegno1() {
		return regno1;
	}
	/**
	 * @return the regno2
	 */
	public Integer getRegno2() {
		return regno2;
	}
	/**
	 * @return the profileGrpId
	 */
	public String getProfileGrpId() {
		return profileGrpId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @param userPw the userPw to set
	 */
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	/**
	 * @param userNm the userNm to set
	 */
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	/**
	 * @param regno1 the regno1 to set
	 */
	public void setRegno1(Integer regno1) {
		this.regno1 = regno1;
	}
	/**
	 * @param regno2 the regno2 to set
	 */
	public void setRegno2(Integer regno2) {
		this.regno2 = regno2;
	}
	/**
	 * @param profileGrpId the profileGrpId to set
	 */
	public void setProfileGrpId(String profileGrpId) {
		this.profileGrpId = profileGrpId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userPw == null) ? 0 : userPw.hashCode());
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
		UserVO other = (UserVO) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userPw == null) {
			if (other.userPw != null)
				return false;
		} else if (!userPw.equals(other.userPw))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userPw=" + userPw + ", userNm=" + userNm + ", regno1=" + regno1
				+ ", regno2=" + regno2 + ", profileGrpId=" +profileGrpId+ "]";
	}
	
    public void mergeFrom(UserVO oldUser) {
        // ---- UserVO 전용 필드 ----
        if (this.userPw == null || this.userPw.isBlank()) {
            this.userPw = oldUser.getUserPw();
        }
        if (this.userNm == null || this.userNm.isBlank()) {
            this.userNm = oldUser.getUserNm();
        }
        if (this.regno1 == null) {
            this.regno1 = oldUser.getRegno1();
        }
        if (this.regno2 == null) {
            this.regno2 = oldUser.getRegno2();
        }
        if (this.profileGrpId == null || this.profileGrpId.isBlank()) {
            this.profileGrpId = oldUser.getProfileGrpId();
        }

        // ---- BaseVO 상속 필드 ----
        if (this.getRgstId() == null || this.getRgstId().isBlank()) {
            this.setRgstId(oldUser.getRgstId());
        }
        if (this.getRgstDt() == null) {
            this.setRgstDt(oldUser.getRgstDt());
        }
        if (this.getUpdtId() == null || this.getUpdtId().isBlank()) {
            this.setUpdtId(oldUser.getUpdtId());
        }
        if (this.getUpdtDt() == null) {
            this.setUpdtDt(oldUser.getUpdtDt());
        }
        if (this.getDelYn() == null || this.getDelYn().isBlank()) {
            this.setDelYn(oldUser.getDelYn());
        }
    }
}
