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

@Controller
@RequestMapping("common")
public class CommonController {
	
	@Autowired
	private CityService cityService;
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value="cities",method=RequestMethod.POST)
	public String cities(Integer id,Model model){
		List<City> cities = cityService.cities(id);
		model.addAttribute("cities", cities);
		return "common/city_option";
	}
	@RequestMapping(value="sendChannelId")
	public void getChannelId(Integer customerId,String channelId){
		customerService.updateChannelId(customerId,channelId);
	}
	
}
