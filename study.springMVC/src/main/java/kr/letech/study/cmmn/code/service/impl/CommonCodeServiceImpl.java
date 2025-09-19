/**
 * 
 */
package kr.letech.study.cmmn.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.letech.study.cmmn.code.dao.CommonCodeDAO;
import kr.letech.study.cmmn.code.service.CommonCodeService;
import kr.letech.study.cmmn.code.vo.CommonCodeVO;
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
 *  2025-09-16		KCY				최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

	private final CommonCodeDAO commonCodeDAO;

	@Override
	public List<CommonCodeVO> readCommonCodeList(List<String> cmmnCodeParams) {
		log.debug("▩▩▩ CommonCodeService .readCommonCodeList() 실행.");
		
		return commonCodeDAO.selectCodeList(cmmnCodeParams);
	}
	
}
