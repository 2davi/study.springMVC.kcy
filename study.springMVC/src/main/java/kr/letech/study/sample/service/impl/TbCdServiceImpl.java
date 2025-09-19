package kr.letech.study.sample.service.impl;

import org.springframework.stereotype.Service;

import kr.letech.study.sample.dao.TbCdDAO;
import kr.letech.study.sample.service.TbCdService;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class TbCdServiceImpl implements TbCdService {

	private final TbCdDAO tbCdDao;
	
	@Override
	public String selectCd(String cd) {
		return tbCdDao.selectCd(cd);
	}

}
