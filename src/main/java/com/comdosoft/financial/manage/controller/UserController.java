package com.comdosoft.financial.manage.controller;

import java.util.Iterator;
import java.util.List;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerAgentRelationService;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.MerchantService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.utils.page.Page;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * @param name
	 * @param password
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value="create",method=RequestMethod.POST)
    @ResponseBody
	public Response createPost(String phone,String name,
			String password,Integer cityId){
		boolean result = customerService.createCustomer(name, password, phone, cityId);
	    if(result){
            return Response.getSuccess(null);
        } else{
            return Response.getError("手机号对应账号已存在！");
        }
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
    @ResponseBody
	public Response editPost(@PathVariable Integer id,Model model,
			String phone,String name,
			String password,Integer cityId){
		boolean result = customerService.update(id, name, password, phone, cityId);
        if(result){
            return Response.getSuccess(null);
        } else{
            return Response.getError("修改失败！");
        }
    }

	/**
	 * 停用/启用
	 * @return
	 */
	@RequestMapping(value="{id}/status",method=RequestMethod.POST)
	public String userStatus(@PathVariable Integer id, String source, Model model){
		Customer customer = customerService.updateStatus(id);
		model.addAttribute("customer", customer);
		long terminal = terminalService.countCustomerTerminals(id);
		model.addAttribute("terminal", terminal);
        if("info".equals(source)){
            return "user/info_status";
        }
		return "user/row";
	}

    @RequestMapping(value="{id}/resetpwd",method=RequestMethod.GET)
    public String resetPassword(@PathVariable Integer id, Model model){
        Customer customer = customerService.selectById(id);
        model.addAttribute("customer", customer);
        return "user/customer_reset_pwd";
    }
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String userInfo(@PathVariable Integer id,Model model){
		Customer customer = customerService.customer(id);
        if(customer.getCityId() != null){
            City city = cityService.selectCityAndParent(customer.getCityId());
            model.addAttribute("city", city);
        }
		model.addAttribute("customer", customer);
		return "user/info";
	}

    @RequestMapping(value="{id}/integral",method=RequestMethod.POST)
    @ResponseBody
    public Response integral(@PathVariable Integer id,String reason, Byte type, Integer num, Model model){
        Customer customer = customerService.modifyIntegral(id, reason, type, num);
        return Response.getSuccess(customer.getIntegral());
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

    @RequestMapping(value="/merchant/{id}/info",method=RequestMethod.GET)
    public String merchantInfo(@PathVariable Integer id, Model model){
        Merchant merchant = merchantService.findMerchantInfo(id);
        model.addAttribute("merchant", merchant);
        if(merchant.getCityId() != null){
            City city = cityService.selectCityAndParent(merchant.getCityId());
            model.addAttribute("city", city);
        }
        return "user/merchant_info";
    }
}
