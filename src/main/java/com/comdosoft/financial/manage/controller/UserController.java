package com.comdosoft.financial.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerService;

/**
 * 用户
 * @author wu
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private CityService cityService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(){
		return "user/list";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String createGet(Model model){
		List<City> cities = cityService.provinces();
		model.addAttribute("cities", cities);
		return "user/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String createPost(String phone,String passport,
			String password,String repassword,Integer city){
		customerService.create(passport, password, phone, city);
		return "redirect:/user/list";
	}

}
