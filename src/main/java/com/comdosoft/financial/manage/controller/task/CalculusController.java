package com.comdosoft.financial.manage.controller.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.CsUpdateInfo;
import com.comdosoft.financial.manage.domain.zhangfu.CsUpdateInfoMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralConvert;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntentionMark;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.service.cs.CsUpdateService;
import com.comdosoft.financial.manage.service.task.CustomerIntegralConvertService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/task/calculus")
public class CalculusController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerIntegralConvertService customerIntegralConvertService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
//		if (null == status || status < 0) status = 0;
//		if ("".equals(keyword)) keyword = null;
		Page<CustomerIntegralConvert> integral = customerIntegralConvertService.findPage(customer, page, status, keyword);
		model.addAttribute("integral", integral);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "task/calculus/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "task/calculus/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(HttpServletRequest request,@PathVariable Integer id, Model model) {
		CustomerIntegralConvert integralInfo = customerIntegralConvertService.findInfoById(id);
		List<CustomerIntentionMark> integralInfoMarks = customerIntegralConvertService.findMarksById(id);
		Customer customer = sessionService.getLoginInfo(request);
		model.addAttribute("user_id", customer.getId());
		model.addAttribute("user_name", customer.getName());
		model.addAttribute("integralInfo", integralInfo);
		model.addAttribute("integralInfoMarks", integralInfoMarks);
		return "task/calculus/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletResponse response, @PathVariable Integer id) {
		customerIntegralConvertService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletResponse response, @PathVariable Integer id) {
		customerIntegralConvertService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletResponse response, @PathVariable Integer id) {
		customerIntegralConvertService.finish(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		customerIntegralConvertService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csUpdateId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CustomerIntentionMark csUpdateMark = customerIntegralConvertService.createMark(customer, csUpdateId, content);
    	model.addAttribute("mark", csUpdateMark);
        return "cs/mark";
    }
}
