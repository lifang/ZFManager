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

import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsLeaseService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/lease")
public class CsLeaseController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsLeaseService csLeaseService;
	@Autowired
	private CsCommonService csCommonService;

	private void findPage(Customer customer, Integer page, Integer status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsLeaseReturn> csLeases = csLeaseService.findPage(customer, page, status, keyword);
		model.addAttribute("csLeases", csLeases);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Integer status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/lease/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Integer status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/lease/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsLeaseReturn csLease = csLeaseService.findInfoById(id);
		List<CsLeaseReturnMark> csLeaseMarks = csLeaseService.findMarksByCsLeaseReturnId(id);
		model.addAttribute("csLease", csLease);
		model.addAttribute("csLeaseMarks", csLeaseMarks);
		if (null != csLease.getCsCencelId() && csLease.getCsCencelId() > 0) {
			List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
			model.addAttribute("materials", materials);
		}
		return "cs/lease/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletResponse response, @PathVariable Integer id) {
		csLeaseService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletResponse response, @PathVariable Integer id) {
		csLeaseService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletResponse response, @PathVariable Integer id) {
		csLeaseService.finish(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csLeaseService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csLeaseId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsLeaseReturnMark csLeaseMark = csLeaseService.createMark(customer, csLeaseId, content);
    	model.addAttribute("mark", csLeaseMark);
        return "cs/mark";
    }
}
