package com.comdosoft.financial.manage.controller.good;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("channel")
public class ChannelController {
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(){
		return "good/channel/list";
	}
}