package com.comdosoft.financial.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.service.GoodProfitService;

@Controller
@RequestMapping("/pos")
public class PosController {
	
	@Autowired
	private GoodProfitService goodProfitService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(){
		return "pos/list";
	}

}
