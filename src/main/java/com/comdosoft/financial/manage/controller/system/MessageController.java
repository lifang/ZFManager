package com.comdosoft.financial.manage.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/system/message")
public class MessageController {
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(){
		return "system/message_list";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String createGet(){
		return "system/message_create";
	}
	
	@RequestMapping(value="/{id}/view",method=RequestMethod.GET)
	public String view(@PathVariable Integer id){
		return "system/message_view";
	}
}
