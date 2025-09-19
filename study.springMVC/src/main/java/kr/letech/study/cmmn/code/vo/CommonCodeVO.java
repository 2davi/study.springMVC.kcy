/**
 * 
 */
package kr.letech.study.cmmn.code.vo;

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
public class CommonCodeVO extends BaseVO implements Serializable{
	private static final long serialVersionUID = -3735628470892609562L;
	
	private String cmmnGrpCd;
	private String cmmnCd;
	private String cmmnNm;
	private String cmmnDesc;
		
	/**
	 * @param cmmnGrpCd
	 * @param cmmnCd
	 * @param cmmnNm
	 * @param cmmnDesc
	 */
	public CommonCodeVO() {}
	public CommonCodeVO(String cmmnGrpCd, String cmmnCd, String cmmnNm, String cmmnDesc) {
		super();
		this.cmmnGrpCd = cmmnGrpCd;
		this.cmmnCd = cmmnCd;
		this.cmmnNm = cmmnNm;
		this.cmmnDesc = cmmnDesc;
	}
	
	/**
	 * @return the cmmnGrpCd
	 */
	public String getCmmnGrpCd() {
		return cmmnGrpCd;
	}
	/**
	 * @return the cmmnCd
	 */
	public String getCmmnCd() {
		return cmmnCd;
	}
	/**
	 * @return the cmmnNm
	 */
	public String getCmmnNm() {
		return cmmnNm;
	}
	/**
	 * @return the cmmnDesc
	 */
	public String getCmmnDesc() {
		return cmmnDesc;
	}
	/**
	 * @param cmmnGrpCd the cmmnGrpCd to set
	 */
	public void setCmmnGrpCd(String cmmnGrpCd) {
		this.cmmnGrpCd = cmmnGrpCd;
	}
	/**
	 * @param cmmnCd the cmmnCd to set
	 */
	public void setCmmnCd(String cmmnCd) {
		this.cmmnCd = cmmnCd;
	}
	/**
	 * @param cmmnNm the cmmnNm to set
	 */
	public void setCmmnNm(String cmmnNm) {
		this.cmmnNm = cmmnNm;
	}
	/**
	 * @param cmmnDesc the cmmnDesc to set
	 */
	public void setCmmnDesc(String cmmnDesc) {
		this.cmmnDesc = cmmnDesc;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cmmnCd == null) ? 0 : cmmnCd.hashCode());
		result = prime * result + ((cmmnGrpCd == null) ? 0 : cmmnGrpCd.hashCode());
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
		CommonCodeVO other = (CommonCodeVO) obj;
		if (cmmnCd == null) {
			if (other.cmmnCd != null)
				return false;
		} else if (!cmmnCd.equals(other.cmmnCd))
			return false;
		if (cmmnGrpCd == null) {
			if (other.cmmnGrpCd != null)
				return false;
		} else if (!cmmnGrpCd.equals(other.cmmnGrpCd))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CommonCodeVO [cmmnGrpCd=" + cmmnGrpCd + ", cmmnCd=" + cmmnCd + ", cmmnNm=" + cmmnNm + ", cmmnDesc="
				+ cmmnDesc + "]";
	}
}
