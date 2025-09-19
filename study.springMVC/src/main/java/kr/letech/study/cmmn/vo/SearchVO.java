/**
 * 
 */
package kr.letech.study.cmmn.vo;

import java.io.Serializable;

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
 *  2025-09-15		KCY				최초 생성
 */
public class SearchVO implements Serializable {
	private static final long serialVersionUID = -1939274069999852142L;
	
	private Integer keyword;
	private String term;
	/**
	 * @param keyword
	 * @param term
	 */
	public SearchVO() {}
	public SearchVO(Integer keyword, String term) {
		super();
		this.keyword = keyword;
		this.term = term;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the keyword
	 */
	public Integer getKeyword() {
		return keyword;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(Integer keyword) {
		this.keyword = keyword;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		SearchVO other = (SearchVO) obj;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SearchVO [keyword=" + keyword + ", term=" + term + "]";
	}
	
}
