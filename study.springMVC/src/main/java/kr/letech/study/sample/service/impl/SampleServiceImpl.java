package kr.letech.study.sample.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import kr.letech.study.sample.dao.SampleDAO;
import kr.letech.study.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {
	
	private final SampleDAO sampleDAO;
	
	private final MessageSource messageSource;
	
	@Override
	public String selectNow() {
		log.info("messageSource : {}", messageSource.getMessage("button.search", null, null));
		
		return sampleDAO.selectNow();
	}
	
}
