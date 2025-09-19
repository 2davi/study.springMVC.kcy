/**
 * 
 */
package kr.letech.study.cmmn.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.cmmn.file.vo.FilesVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * < 개정이력 >
 * 
 * 수정일 수정자 수정내용 ------------------------------------------------ 2025-09-17 KCY
 * 최초 생성
 */
@Component
@Slf4j
public class FileStorageUtils {
	
	private static final String REPO_DIR = "D:/java/eclipse/eGovFrameDev-4.0.0-64bit/eclipse/study.SpringMVC";

	// FILES 테이블의 FILE_DIR 값. 추후 동적으로 처리되게끔 수정하자.
	private static final String FILE_DIR_USER = "user";
	private static final String FILE_DIR_BOARD = "board";

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.upload()</i><br />
	 * <b>파일 저장소에 실제 파일 단건 저장</b><br />
	 * <br />
	 * 
	 * @param multipart
	 * @param param_attachType
	 * @param userId
	 */
	@SuppressWarnings("finally")
	public static FilesVO upload(MultipartFile multipart, String param_attachType, String userId) throws IOException {
		log.debug("▩▩▩ FileStorageUtil .upload() 실행.");

		FilesVO fileVO = new FilesVO();

		if (multipart.isEmpty()) {
			log.debug("▩▩▩ FileStorageUtil .upload( NULL ) - 파일이 없습니다.");
		}

		String fileGroupId = UUID.randomUUID().toString();
		String fileOriginalName = multipart.getOriginalFilename();
		String withoutExtention = StringUtils.substringBeforeLast(fileOriginalName, ".");
		String fileReferenceName = FILE_DIR_BOARD + "-" + fileGroupId + "-" + withoutExtention;
		Long fileSize = multipart.getSize();
		String fileMimeType = multipart.getContentType();

		// 이 파일이 정책에 맞는지 검증하는 절차 추후 구현
		// 1. 정상적인 파일이 맞는지 서버단 체크
		// 2. 업로드할 파일의 VO데이터 미리 준비
		// 3. 실제 서버에 업로드
		// 4. 업로드 성공 여부에 따라 반환값 분기
		
		// (2)
		fileVO.setFileGrpId(fileGroupId);
		fileVO.setFileSeq(1);
		fileVO.setFileOrgNm(fileOriginalName);
		fileVO.setFileRefNm(fileReferenceName);
		fileVO.setFileSize(fileSize);
		fileVO.setFileDir(FILE_DIR_USER);
		fileVO.setAttachType(param_attachType);
		fileVO.setMimeType(fileMimeType);
		fileVO.setRgstId(userId);
		fileVO.setUpdtId(userId);

		// (3)
		String filePath = REPO_DIR + File.separatorChar + FILE_DIR_USER;
		FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(filePath, fileReferenceName));

		log.debug("▩▩▩ SUCCESS - 파일 업로드 성공적. [VO객체 반환]");
		return fileVO;
	}

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.upload()</i><br />
	 * <b>파일 저장소에 실제 파일 다건 저장</b><br />
	 * <br />
	 * 
	 * @param multiparts
	 * @param param_attachType
	 * @param userId
	 */
	public static List<FilesVO> upload(MultipartFile[] multiparts, String param_attachType, String userId) throws IOException {
		log.debug("▩▩▩ FileStorageUtil .upload() 실행.");

		//파일 업로드에 성공했는가 기본값 OK,
		//실패 시 업로드 중단
		Boolean isOk = true;
		
		if(multiparts != null) {
			
			List<FilesVO> fileVOList = new ArrayList<>();
			

			String filePath = REPO_DIR + File.separatorChar + FILE_DIR_BOARD;
			String fileGroupId = UUID.randomUUID().toString();
			Integer seq = 0;
			
			
			//로컬 디렉토리에 파일 여러 건 업로드
			for(int i=0; i < multiparts.length; i++) {
				MultipartFile multipart = multiparts[i];
				seq++;
				
				if(!multipart.isEmpty()) {
					
					String fileOriginalName = multipart.getOriginalFilename();
					String withoutExtention = StringUtils.substringBeforeLast(fileOriginalName, ".");
					String fileReferenceName = FILE_DIR_BOARD + "-" + fileGroupId + "-" + withoutExtention;
					Long fileSize = multipart.getSize();
					String fileMimeType = multipart.getContentType();
							log.debug("▩ ----- MIME-TYPE: {}", fileMimeType);
					
					// 이 파일이 정책에 맞는지 검증하는 절차 추후 구현 (ApacheTika)
					// 1. 정상적인 파일이 맞는지 서버단 체크
					// 2. 업로드할 파일의 VO데이터 미리 준비
					// 3. 실제 서버에 업로드
					// --X. 업로드 성공 여부에 따라 반환값 분기
					// 4. VO데이터 리스트에 추가
					// 5. 반복
					
					// (2)
					FilesVO fileVO = new FilesVO();
					
					fileVO.setFileGrpId(fileGroupId);
					fileVO.setFileSeq(seq);
					fileVO.setFileOrgNm(fileOriginalName);
					fileVO.setFileRefNm(fileReferenceName);
					fileVO.setFileSize(fileSize);
					fileVO.setFileDir(FILE_DIR_BOARD);
					fileVO.setAttachType(param_attachType);
					fileVO.setMimeType(fileMimeType);
					fileVO.setRgstId(userId);
					fileVO.setUpdtId(userId);
					
					// (3)
					FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(filePath, fileReferenceName));
					
					//(4)
					fileVOList.add(fileVO);
					
				}//if문 end.
			}//for문 end.
			
			return fileVOList;
		} else {
			return null;
		}
	}

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.overwrite()</i><br />
	 * <b>파일 저장소에 저장된 파일 덮어쓰기</b><br />
	 * <br />
	 * 
	 * @param file
	 * @param filePath
	 */
	public static void overwrite(MultipartFile file, String filePath) {
		log.debug("▩▩▩ FileStorageUtil .overwrite() 실행.");

	}

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.purge()</i><br />
	 * <b>파일 저장소에서 저장된 실제 파일의 단건 물리 삭제</b><br />
	 * <br />
	 * 
	 * @param filePath
	 */
	public static void purge(String filePath) {
		log.debug("▩▩▩ FileStorageUtil .purge() 실행.");

	}

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.load()</i><br />
	 * <b>파일 저장소에서 실제 파일 불러오기</b><br />
	 * <br />
	 * 
	 * @param filePath
	 */
	public static void load(String filePath) {
		log.debug("▩▩▩ FileStorageUtil .load() 실행.");

	}
	
	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.download()</i><br />
	 * <b>파일 저장소에 저장된 파일 다운로드 실행</b><br />
	 * <br />
	 * 
	 * @param filePath
	 * @param response
	 */
	public static void download(String filePath, HttpServletResponse response) {
		log.debug("▩▩▩ FileStorageUtil .download() 실행.");

	}

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.generateGroupId()</i><br />
	 * <b>유틸성: FilesVO에 쓰일 FILE_GRP_ID 생성</b><br />
	 * <br />
	 * 
	 * @param fileOriginalName
	 */
	private void generateGroupId(FilesVO fileVO) {
		log.debug("▩▩▩ FileStorageUtil .generateGroupId() 실행.");

		String fileOriginalName = fileVO.getFileOrgNm();
		String fileSize = String.valueOf(fileVO.getFileSize());
		String folder = fileVO.getFileDir();

	}

	/**
	 * <i>kr.letech.study.cmmn.utils.FileStorageUtil.isFileExists()</i><br />
	 * <b>유틸성: 파일의 존재 여부</b><br />
	 * <br />
	 * 
	 * @param filePath
	 */
	private void isFileExists(String filePath) {
		log.debug("▩▩▩ FileStorageUtil .isFileExists() 실행.");

	}
}
