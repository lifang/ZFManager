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
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsChangeService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/change")
public class CsChangeController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsChangeService csChangeService;
	@Autowired
	private CsCommonService csCommonService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsChange> csChanges = csChangeService.findPage(customer, page, status, null != keyword ? keyword.trim() : keyword);
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
		if (null != csChange.getCsCencelId() && csChange.getCsCencelId() > 0) {
			List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
			model.addAttribute("materials", materials);
		}
		return "cs/change/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csChangeService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csChangeService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csChangeService.finish(id);
	}
	
	@RequestMapping(value = "{id}/confirm", method = RequestMethod.POST)
	public void confirmChange(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id, CsReceiverAddress csReceiverAddress) {
		Customer customer = sessionService.getLoginInfo(request);
		csChangeService.confirm(id, csReceiverAddress, customer);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csChangeService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "{id}/mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, @PathVariable Integer id, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsChangeMark csChangeMark = csChangeService.createMark(customer, id, content);
    	model.addAttribute("mark", csChangeMark);
        return "cs/mark";
    }
}
