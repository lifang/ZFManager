package com.comdosoft.financial.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.service.CityService;

@Controller
@RequestMapping("common")
public class CommonController {
	
	@Autowired
	private CityService cityService;

	@RequestMapping(value="cities",method=RequestMethod.POST)
	public String cities(Integer id,Model model){
		List<City> cities = cityService.cities(id);
		model.addAttribute("cities", cities);
		return "common/city_option";
	}
	
}
