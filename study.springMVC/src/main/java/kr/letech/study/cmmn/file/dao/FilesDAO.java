/**
 * 
 */
package kr.letech.study.cmmn.file.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface FilesDAO {

	public FilesVO selectFile(Map<String, String> params);
	public List<FilesVO> selectFileList(String fileGrpId);
	public Integer insertFile(FilesVO file);
	public Integer insertFileList(List<FilesVO> file);
	public Integer updateFile(FilesVO file);
	public Integer mergeFile(FilesVO file);
	
	/**
	 * 삭제할 파일의 정보: String userId, String fileGrpId
	 * @param param
	 * @return
	 */
	public Integer deleteFile(Map<String, String> param);
	public Integer deleteFiles(String fileGrpId, String userId, List<String> deleteFileSeqList);
	
	
	public String selectFileRefNm(String profileGrpId);
	public List<String> selectFileRefNmList(String userId, String attachType);
	public List<Map<String, Object>> selectAttachFileCount(List<String> attachGrpIdList);
}
