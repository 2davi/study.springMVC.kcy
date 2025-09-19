/**
 * 
 */
package kr.letech.study.cmmn.file.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.file.vo.FilesVO;

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
public interface FileService {


	
	public List<FilesVO> readAttachFileList(String attachGrpId);

	public FilesVO createFile(MultipartFile multipart, String attachType, String userId);
	public List<FilesVO> createFile(MultipartFile[] multiparts, String attachType, String userId);
	public FilesVO modifyFile(MultipartFile multipart, String attachType, String userId, String fileGrpId_nullable);
	public void removeFile(String userId, String fileGrpId);
	
	/** 간단 기능 */
	public String readUserProfileImgSrc(String profileGrpId);
	public List<Map<String, Object>> readAttachFileCount(List<String> attachGrpIdList);
}
