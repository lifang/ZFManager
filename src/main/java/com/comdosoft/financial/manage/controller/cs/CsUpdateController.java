package com.comdosoft.financial.manage.controller.cs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("cs/update")
public class CsUpdateController {

	private static final Logger LOG = LoggerFactory.getLogger(CsUpdateController.class);

	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		return "cs/update/list";
	}
}
