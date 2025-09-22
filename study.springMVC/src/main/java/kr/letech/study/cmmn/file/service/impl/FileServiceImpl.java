/**
 * 
 */
package kr.letech.study.cmmn.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
	public List<FilesVO> createFile(MultipartFile[] multiparts, String attachType, String userId){
		log.debug("▩▩▩ FileService .createFile() 호출.");
		
		List<FilesVO> fileVOList = new ArrayList<>();
		
		try {
			fileVOList = FileStorageUtils.upload(multiparts, attachType, userId);
			
			log.debug("▩ ----- fileVOList: {}", fileVOList.size());
			fileDAO.insertFileList(fileVOList);
		} catch (IOException e) {
			e.printStackTrace();
			fileVOList = null;
		} finally {
			return fileVOList;			
		}
	}
	
	@Override @SuppressWarnings("finally")
	public List<FilesVO> createFile(MultipartFile[] multiparts, List<Long> lastModifiedList, String attachType, String userId, String attachGrpId) {
		log.debug("▩▩▩ FileService .createFile() 호출.");
		
		List<FilesVO> fileVOList = new ArrayList<>();
		String sequence = null;
		
		//---분기: 기존 파일의 수정 여부 확인 후 논리삭제
		if(attachGrpId != null) {
			//몽땅 삭제처리하고 업로드할 때 UPSERT할까?
			removeFile(userId, attachGrpId);
			sequence = fileDAO.selectLastSeq(attachGrpId);
		}
		
		
		//신규 파일 업로드
		try {
			fileVOList = FileStorageUtils.upload(multiparts, lastModifiedList, attachType, userId, attachGrpId, sequence);
			
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
	public void removeFiles(String userId, String fileGrpId, List<String> deleteFileSeqList) {
		log.debug("▩▩▩ FileService .removeFiles() 호출.");
		
		fileDAO.deleteFiles(fileGrpId, userId, deleteFileSeqList);
	}
	
	@Override
	public void downloadFile(String fileGrpId, String fileSeq, HttpServletResponse response) {
		log.debug("▩▩▩ FileService .downloadFile() 호출.");
		
	    /*
	     * 1. 등록된 첨부파일 정보 조회
	     * 2. 다운로드 가능 여부 검증
	     * 3. 다운로드 실행
	     */
		log.debug("▩ ----- 파라미터 체크: {}, {}", fileGrpId, fileSeq);
	    FilesVO fileVO = fileDAO.selectFile(Map.of("fileGrpId", fileGrpId, "fileSeq", fileSeq));

	    if (fileVO == null) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        return;
	    }

	    // 파일유틸 내에서 제공하는 기능으로 만들어보자.
	    String filePath = FileStorageUtils.REPO_DIR
	            + File.separatorChar + fileVO.getFileDir()
	            + File.separatorChar + fileVO.getFileRefNm();

	    FileStorageUtils.download(fileVO.getFileOrgNm(), filePath, response);
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
