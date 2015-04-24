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

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralConvert;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralConvertMark;
import com.comdosoft.financial.manage.service.CustomerIntegralConvertMarkService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.task.CustomerIntegralConvertService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/task/calculus")
public class CalculusController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerIntegralConvertMarkService customerIntegralConvertMarkService;
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
		List<CustomerIntegralConvertMark> integralInfoMarks = customerIntegralConvertMarkService.findMarksById(id);
		Customer customer = sessionService.getLoginInfo(request);
		model.addAttribute("user_id", customer.getId());
		model.addAttribute("user_name", customer.getName());
		model.addAttribute("integralInfo", integralInfo);
		model.addAttribute("integralInfoMarks", integralInfoMarks);
		return "task/calculus/info";
	}
	
//	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
//	public void handle(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
//		customerIntegralConvertService.handle(id);
//		Customer customer = sessionService.getLoginInfo(request);
//		customerIntegralConvertService.record(customer,id,"标记为处理中");
//	}
//	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
		customerIntegralConvertService.cancel(id);
		Customer customer = sessionService.getLoginInfo(request);
		customerIntegralConvertService.record(customer,id,"取消");
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request,HttpServletResponse response, @PathVariable Integer id) {
		Customer customer = sessionService.getLoginInfo(request);
		customerIntegralConvertService.finish(id);
		customerIntegralConvertService.record(customer,id,"标记为完成");
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		customerIntegralConvertService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csUpdateId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CustomerIntegralConvertMark csUpdateMark = customerIntegralConvertMarkService.createMark(customer, csUpdateId, content);
    	model.addAttribute("mark", csUpdateMark);
        return "cs/mark";
    }
}
