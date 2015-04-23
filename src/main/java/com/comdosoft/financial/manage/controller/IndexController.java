package com.comdosoft.financial.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.IndexMessageService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
public class IndexController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private IndexMessageService indexMessageService;
	@Value("${url.login}")
	private String loginUrl;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String root(HttpServletRequest request){
		if(sessionService.isLogged(request)){
			return "redirect:/index";
		}
		return "redirect:"+loginUrl;
	}
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,Model model){
		IndexMessage(model);
		
        Customer customer = sessionService.getLoginInfo(request);
        if(customer.getTypes() == Customer.TYPE_THIRD_PARTY){
            return "factory_role/index";
        }
        return "index";
	}
	
	/**
	 * 首页数据
	 * @param model
	 * @return
	 */
	public void IndexMessage(Model model){
		model.addAttribute("commentSum",indexMessageService.CommentModeration());
		model.addAttribute("goodSum",indexMessageService.GoodModeration());
		model.addAttribute("openingAppliesSum",indexMessageService.openingAppliesModeration());
		model.addAttribute("returnSum",indexMessageService.returnModeration());
		model.addAttribute("changeSum",indexMessageService.changeModeration());
		model.addAttribute("repairsSum",indexMessageService.repairsModeration());
		model.addAttribute("cancelsSum",indexMessageService.cancelsModeration());
		model.addAttribute("updateInfosSum",indexMessageService.updateInfosModeration());
		model.addAttribute("storagesSum",indexMessageService.storagesModeration());
		model.addAttribute("srefundsSum",indexMessageService.srefundsModeration());
		model.addAttribute("agentSum",indexMessageService.agentModeration());
	}
}
