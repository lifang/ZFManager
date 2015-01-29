package com.comdosoft.financial.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.service.GoodProfitService;

@Controller
public class BaseController {
	
	@Autowired
	private GoodProfitService goodProfitService;
	
	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public String test(){
		goodProfitService.create();
		return "test";
	}

}
