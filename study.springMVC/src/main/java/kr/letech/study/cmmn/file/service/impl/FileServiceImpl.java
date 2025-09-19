/**
 * 
 */
package kr.letech.study.cmmn.file.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.file.dao.FilesDAO;
import kr.letech.study.cmmn.file.service.FileService;
import kr.letech.study.cmmn.file.vo.FilesVO;
import kr.letech.study.cmmn.utils.FileStorageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Service @Slf4j @RequiredArgsConstructor
public class FileServiceImpl implements FileService {
	
	private final FilesDAO fileDAO;

	
	@Override
	public List<FilesVO> readAttachFileList(String attachGrpId) {
		log.debug("▩▩▩ FileService .readAttachFileList() 호출.");
		
		List<FilesVO> attachFileList = null;
		
		attachFileList = fileDAO.selectFileList(attachGrpId);
		
		return attachFileList;
	}

	
	@Override
	public FilesVO createFile(MultipartFile multipart, String attachType, String userId) {
		log.debug("▩▩▩ FileService .createFile() 호출.");
//		Boolean isOk = null;
		FilesVO fileVO = null;
		
		try {
			fileVO = FileStorageUtils.upload(multipart, attachType, userId);
			fileDAO.insertFile(fileVO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileVO;
	}
	
	@Override @SuppressWarnings("finally")
	public List<FilesVO> createFile(MultipartFile[] multiparts, String attacyType, String userId){
		log.debug("▩▩▩ FileService .createFile() 호출.");
		
		List<FilesVO> fileVOList = new ArrayList<>();
		
		try {
			fileVOList = FileStorageUtils.upload(multiparts, attacyType, userId);
			
			log.debug("▩ ----- fileVOList: {}", fileVOList.size());
			fileDAO.insertFileList(fileVOList);
		} catch (IOException e) {
			e.printStackTrace();
			fileVOList = null;
		} finally {
			return fileVOList;			
		}
	}
	
	@Override
	public FilesVO modifyFile(MultipartFile multipart, String attachType, String userId, String fileGrpId_nullable) {
		log.debug("▩▩▩ FileService .modifyFile() 호출.");
		log.debug("▩ ----- 넘겨받은 multipart: {}", multipart);
		
		//일단 파일이 있을 때만 들어옴.
		FilesVO fileVO = null;
		
		//기존 사용자 정보에 프로필GroupID가 있으면 논리삭제 진행, 없으면 새로 create.
		if(fileGrpId_nullable != null) {
			log.debug("▩ ----- 기존의 프로필이미지 존재. 삭제처리 진행.");
			removeFile(userId, fileGrpId_nullable);
		}
		log.debug("▩ ----- 삭제 완료");
		
		fileVO = createFile(multipart, attachType, userId);
		
		return fileVO;
	}
	
	@Override
	public void removeFile(String userId, String fileGrpId) {
		log.debug("▩▩▩ FileService .removeFile() 호출.");
		
		Map<String, String> param = Map.of(
				"userId", userId, 
				"fileGrpId", fileGrpId
				);
		fileDAO.deleteFile(param);
	}
	
	@Override
	public String readUserProfileImgSrc(String profileGrpId) {
		log.debug("▩▩▩ FileService .readUserProfileImg() 호출.");
		
		log.debug("▩ ----- 존재하는지 여부 확인: PROFILE_GRP_ID : {}", profileGrpId);
		
		if(profileGrpId == null || profileGrpId.isBlank()) {
			profileGrpId = "aae74513-54fe-4dda-8060-9bcc85ad5ba8";
		}
		
		log.debug("▩ ----- 값 채워놨는지 확인: PROFILE_GRP_ID : {}", profileGrpId);
		
		String fileRefNm = fileDAO.selectFileRefNm(profileGrpId);
		
		
		// servlet-context.xml에 적용한 mvc:resource 태그
		return "/user-profile/" + fileRefNm;
	}
	
	@Override
	public List<Map<String, Object>> readAttachFileCount(List<String> attachGrpIdList) {
		log.debug("▩▩▩ FileService .readAttachFileCount() 호출.");
		
		List<Map<String, Object>> attachFileCount = null;
		
		attachFileCount = fileDAO.selectAttachFileCount(attachGrpIdList);
		
		return attachFileCount;
	}

	
	
}
