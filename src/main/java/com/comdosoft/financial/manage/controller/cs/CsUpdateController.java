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

import com.comdosoft.financial.manage.domain.zhangfu.CsUpdateInfo;
import com.comdosoft.financial.manage.domain.zhangfu.CsUpdateInfoMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.service.cs.CsUpdateService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/update")
public class CsUpdateController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsUpdateService csUpdateService;
	@Autowired
	private CsCommonService csCommonService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsUpdateInfo> csUpdates = csUpdateService.findPage(customer, page, status, keyword);
		model.addAttribute("csUpdates", csUpdates);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/update/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/update/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsUpdateInfo csUpdate = csUpdateService.findInfoById(id);
		List<CsUpdateInfoMark> csUpdateMarks = csUpdateService.findMarksByCsUpdateId(id);
		List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.UPDATE);
		model.addAttribute("csUpdate", csUpdate);
		model.addAttribute("csUpdateMarks", csUpdateMarks);
		model.addAttribute("materials", materials);
		return "cs/update/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csUpdateService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csUpdateService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csUpdateService.finish(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csUpdateService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csUpdateId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsUpdateInfoMark csUpdateMark = csUpdateService.createMark(customer, csUpdateId, content);
    	model.addAttribute("mark", csUpdateMark);
        return "cs/mark";
    }
}
