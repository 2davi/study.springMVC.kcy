/**
 * 
 */
package kr.letech.study.sample;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
 *  2025-09-15		KCY				_DT 필드 String -> LocalDateTime 타입변경
 *  								사용중인 myBatis 3.5.11버전. 기본적으로 바인딩 지원한다.
 *  								-> Date로 변경 : fmt태그가 LDT 지원 안함
 */
public class BaseVO{
	private String rgstId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date rgstDt;
	private String updtId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date updtDt;
	private String delYn;
	
	/**
	 * @param rgstId
	 * @param rgstDt
	 * @param updtId
	 * @param updtDt
	 * @param delYn
	 */
	public BaseVO() {
		this.delYn = "N";
	}
	public BaseVO(String rgstId, Date rgstDt, String updtId, Date updtDt, String delYn) {
		super();
		this.rgstId = rgstId;
		this.rgstDt = rgstDt;
		this.updtId = updtId;
		this.updtDt = updtDt;
		this.delYn = delYn;
	}
	
	/**
	 * @return the rgstId
	 */
	public String getRgstId() {
		return rgstId;
	}
	/**
	 * @return the rgstDt
	 */
	public Date getRgstDt() {
		return rgstDt;
	}
	/**
	 * @return the updtId
	 */
	public String getUpdtId() {
		return updtId;
	}
	/**
	 * @return the updtDt
	 */
	public Date getUpdtDt() {
		return updtDt;
	}
	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}
	/**
	 * @param rgstId the rgstId to set
	 */
	public void setRgstId(String rgstId) {
		this.rgstId = rgstId;
	}
	/**
	 * @param rgstDt the rgstDt to set
	 */
	public void setRgstDt(Date rgstDt) {
		this.rgstDt = rgstDt;
	}
	/**
	 * @param updtId the updtId to set
	 */
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	/**
	 * @param updtDt the updtDt to set
	 */
	public void setUpdtDt(Date updtDt) {
		this.updtDt = updtDt;
	}
	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delYn == null) ? 0 : delYn.hashCode());
		result = prime * result + ((rgstDt == null) ? 0 : rgstDt.hashCode());
		result = prime * result + ((rgstId == null) ? 0 : rgstId.hashCode());
		result = prime * result + ((updtDt == null) ? 0 : updtDt.hashCode());
		result = prime * result + ((updtId == null) ? 0 : updtId.hashCode());
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
		BaseVO other = (BaseVO) obj;
		if (delYn == null) {
			if (other.delYn != null)
				return false;
		} else if (!delYn.equals(other.delYn))
			return false;
		if (rgstDt == null) {
			if (other.rgstDt != null)
				return false;
		} else if (!rgstDt.equals(other.rgstDt))
			return false;
		if (rgstId == null) {
			if (other.rgstId != null)
				return false;
		} else if (!rgstId.equals(other.rgstId))
			return false;
		if (updtDt == null) {
			if (other.updtDt != null)
				return false;
		} else if (!updtDt.equals(other.updtDt))
			return false;
		if (updtId == null) {
			if (other.updtId != null)
				return false;
		} else if (!updtId.equals(other.updtId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BaseVO [rgstId=" + rgstId + ", rgstDt=" + rgstDt + ", updtId=" + updtId + ", updtDt=" + updtDt
				+ ", delYn=" + delYn + "]";
	}

	
}
