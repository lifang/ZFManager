package com.comdosoft.financial.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/pos")
public class PosController {
	
	@Autowired
	private GoodService goodService ;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Model model){
		Page<Good> goods = goodService.findPages(1, 1, null, null);
		for (Good good : goods.getContent()) {
			System.out.println(good.getFactory());
		}
		model.addAttribute(goods);
		return "pos/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(){
		return "pos/page";
	}

}
