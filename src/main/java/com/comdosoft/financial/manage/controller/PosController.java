package com.comdosoft.financial.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/pos")
public class PosController {
	
	@Autowired
	private GoodService goodService ;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Integer status, String keys, Model model){
		findPage(page, status, keys, model);
		return "pos/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Integer status, String keys, Model model){
		findPage(page, status, keys, model);
		return "pos/pagePos";
	}
	
	private void findPage(Integer page, Integer status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Good> goods = goodService.findPages(page, Constants.PAGE_SIZE, status, keys);
		model.addAttribute("goods", goods);
	}

}
