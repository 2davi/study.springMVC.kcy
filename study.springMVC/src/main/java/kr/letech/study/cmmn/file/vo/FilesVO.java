/**
 * 
 */
package kr.letech.study.cmmn.file.vo;

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
 *  2025-09-17		KCY				최초 생성
 */
public class FilesVO extends BaseVO implements Serializable{
	private static final long serialVersionUID = -321714953348354565L;
	
	private String  fileGrpId;
	private Integer fileSeq;
	private String  fileOrgNm;
	private String  fileRefNm;
	private Long    fileSize;
	private String  fileDir;
	private String  attachType;
	private String  mimeType;
	private Long    lastModified;
	
	
	/**
	 * @param fileGrpId
	 * @param fileSeq
	 * @param fileOrgNm
	 * @param fileRefNm
	 * @param fileSize
	 * @param fileDir
	 * @param attachType
	 */
	public FilesVO() {}
	public FilesVO(String fileGrpId, Integer fileSeq, String fileOrgNm, String fileRefNm, Long fileSize, String fileDir,
			String attachType, String mimeType, Long lastModified) {
		super();
		this.fileGrpId = fileGrpId;
		this.fileSeq = fileSeq;
		this.fileOrgNm = fileOrgNm;
		this.fileRefNm = fileRefNm;
		this.fileSize = fileSize;
		this.fileDir = fileDir;
		this.attachType = attachType;
		this.mimeType = mimeType;
		this.lastModified = lastModified;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFileGrpId() {
		return fileGrpId;
	}
	public Integer getFileSeq() {
		return fileSeq;
	}
	public String getFileOrgNm() {
		return fileOrgNm;
	}
	public String getFileRefNm() {
		return fileRefNm;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public String getFileDir() {
		return fileDir;
	}
	public String getAttachType() {
		return attachType;
	}
	public String getMimeType() {
		return mimeType;
	}
	public Long getLastModified() {
		return lastModified;
	}
	public void setFileGrpId(String fileGrpId) {
		this.fileGrpId = fileGrpId;
	}
	public void setFileSeq(Integer fileSeq) {
		this.fileSeq = fileSeq;
	}
	public void setFileOrgNm(String fileOrgNm) {
		this.fileOrgNm = fileOrgNm;
	}
	public void setFileRefNm(String fileRefNm) {
		this.fileRefNm = fileRefNm;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fileGrpId == null) ? 0 : fileGrpId.hashCode());
		result = prime * result + ((fileSeq == null) ? 0 : fileSeq.hashCode());
		result = prime * result + ((fileSize == null) ? 0 : fileSize.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
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
		FilesVO other = (FilesVO) obj;
		if (fileGrpId == null) {
			if (other.fileGrpId != null)
				return false;
		} else if (!fileGrpId.equals(other.fileGrpId))
			return false;
		if (fileSeq == null) {
			if (other.fileSeq != null)
				return false;
		} else if (!fileSeq.equals(other.fileSeq))
			return false;
		if (fileSize == null) {
			if (other.fileSize != null)
				return false;
		} else if (!fileSize.equals(other.fileSize))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FileVO [fileGrpId=" + fileGrpId + ", fileSeq=" + fileSeq + ", fileOrgNm=" + fileOrgNm + ", fileRefNm="
				+ fileRefNm + ", fileSize=" + fileSize + ", fileDir=" + fileDir + ", attachType=" + attachType + ", mimeType=" + mimeType + ", lastModified=" + lastModified + "]";
	}
}
