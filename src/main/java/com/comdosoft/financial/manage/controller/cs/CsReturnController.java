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

import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsReturnService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/return")
public class CsReturnController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsReturnService csReturnService;
	@Autowired
	private CsCommonService csCommonService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsReturn> csReturns = csReturnService.findPage(customer, page, status, keyword);
		model.addAttribute("csReturns", csReturns);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/return/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/return/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsReturn csReturn = csReturnService.findInfoById(id);
		List<CsReturnMark> csReturnMarks = csReturnService.findMarksByCsReturnId(id);
		model.addAttribute("csReturn", csReturn);
		model.addAttribute("csReturnMarks", csReturnMarks);
		if (null != csReturn.getCsCencelId() && csReturn.getCsCencelId() > 0) {
			List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
			model.addAttribute("materials", materials);
		}
		return "cs/return/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletResponse response, @PathVariable Integer id) {
		csReturnService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletResponse response, @PathVariable Integer id) {
		csReturnService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletResponse response, @PathVariable Integer id) {
		csReturnService.finish(id);
	}
	
	@RequestMapping(value = "{csReturnId}/confirm", method = RequestMethod.POST)
	public void confirm(CsReceiverAddress csReceiverAddress, HttpServletResponse response, @PathVariable Integer csReturnId) {
		csReturnService.confirm(csReturnId, csReceiverAddress);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csReturnService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csReturnId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsReturnMark csReturnMark = csReturnService.createMark(customer, csReturnId, content);
    	model.addAttribute("mark", csReturnMark);
        return "cs/mark";
    }
}
