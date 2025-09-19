package kr.letech.study.sample.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.letech.study.sample.service.TbCdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TbCdController {

	private final TbCdService tbCdService;
	
	private final MessageSource messageSource;
	
	@RequestMapping(value="/tb", method=RequestMethod.GET)
	public String tb(Model model, @RequestParam(name = "cd") String cd) {
		log.debug("받아 온 파라미터: {}", cd);
		log.debug("messageSource : {}", messageSource.getMessage("button.search", null, null));
		model.addAttribute("cd", cd);
		model.addAttribute("grpCd", tbCdService.selectCd(cd));
		
		return "tbCd/tbCd";
	}
}
