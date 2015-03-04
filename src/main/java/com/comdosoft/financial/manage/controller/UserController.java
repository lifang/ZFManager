package com.comdosoft.financial.manage.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerAgentRelationService;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.MerchantService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.utils.page.Page;
import com.google.common.collect.Lists;

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
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private CustomerAgentRelationService customerAgentRelationService;
	
	/**
	 * 用户列表
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(){
		return "user/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page,Model model,String query){
		if(page==null) {
			page = 0;
		}
		Page<Customer> customers = customerService.listCustomerPage(page, query);
		List<Long> terminals = Lists.newArrayList();
		Iterator<Customer> it = customers.getContent().iterator();
		while(it.hasNext()){
			terminals.add(terminalService.countCustomerTerminals(it.next().getId()));
		}
		model.addAttribute("customers", customers);
		model.addAttribute("terminals", terminals);
		return "user/page";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String createGet(Model model){
		List<City> cities = cityService.provinces();
		model.addAttribute("cities", cities);
		return "user/create";
	}
	
	/**
	 * 创建用户，表单提交
	 * @param phone
	 * @param passport
	 * @param password
	 * @param repassword
	 * @param city
	 * @return
	 */
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String createPost(String phone,String passport,
			String password,String repassword,Integer city){
		customerService.createCustomer(passport, password, phone, city);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String editGet(@PathVariable Integer id,Model model){
		
		List<City> provinces = cityService.provinces();
		model.addAttribute("provinces", provinces);
		
		Customer customer = customerService.customer(id);
		model.addAttribute("customer", customer);
		
		City city = cityService.city(customer.getCityId());
		model.addAttribute("city", city);
		
		List<City> cities = cityService.cities(city.getParentId());
		model.addAttribute("cities", cities);
		return "user/edit";
	}
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.POST)
	public String editPost(@PathVariable Integer id,Model model,
			String phone,String passport,
			String password,String repassword,Integer city){
		customerService.update(id, passport, password, phone, city);
		return "redirect:/user/list";
	}

	/**
	 * 停用/启用
	 * @return
	 */
	@RequestMapping(value="{id}/status",method=RequestMethod.POST)
	public String userStatus(@PathVariable Integer id,Model model){
		Customer customer = customerService.updateStatus(id);
		model.addAttribute("customer", customer);
		long terminal = terminalService.countCustomerTerminals(id);
		model.addAttribute("terminal", terminal);
		return "user/row";
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String userInfo(@PathVariable Integer id,Model model){
		Customer customer = customerService.customer(id);
		model.addAttribute("customer", customer);
		return "user/info";
	}
	
	@RequestMapping(value="{id}/info/merchants",method=RequestMethod.POST)
	public String userInfoMerchants(@PathVariable Integer id,Integer page,Model model){
		Page<Merchant> merchants = merchantService.userMerchantPage(id, page);
		model.addAttribute("merchants", merchants);
		return "user/info_merchants";
	}
	
	@RequestMapping(value="{id}/info/terminals",method=RequestMethod.POST)
	public String userInfoTerminals(@PathVariable Integer id,Integer page,Model model){
		Page<Terminal> terminals = terminalService.customerTerminalPage(id, page);
		model.addAttribute("terminals", terminals);
		return "user/info_terminals";
	}
	
	@RequestMapping(value="{id}/info/agents",method=RequestMethod.POST)
	public String userInfoAgents(@PathVariable Integer id,Integer page,Model model){
		Page<Agent> agents = customerAgentRelationService.customerAgents(id, page);
		model.addAttribute("agents", agents);
		return "user/info_agents";
	}
}
