package com.comdosoft.financial.manage.controller.cs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.CsChange;
import com.comdosoft.financial.manage.domain.zhangfu.CsChangeMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsChangeService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/change")
public class CsChangeController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsChangeService csChangeService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsChange> csChanges = csChangeService.findPage(customer, page, status, keyword);
		model.addAttribute("csChanges", csChanges);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/change/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/change/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsChange csChange = csChangeService.findInfoById(id);
		List<CsChangeMark> csChangeMarks = csChangeService.findMarksByCsChangeId(id);
		model.addAttribute("csChange", csChange);
		model.addAttribute("csChangeMarks", csChangeMarks);
		return "cs/change/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletResponse response, @PathVariable Integer id) {
		csChangeService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletResponse response, @PathVariable Integer id) {
		csChangeService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletResponse response, @PathVariable Integer id) {
		csChangeService.finish(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csChangeService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csChangeId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsChangeMark csChangeMark = csChangeService.createMark(customer, csChangeId, content);
    	model.addAttribute("mark", csChangeMark);
        return "cs/mark";
    }
}
