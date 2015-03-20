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

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsAgentMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsAgentMarkService;
import com.comdosoft.financial.manage.service.cs.CsAgentService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/agent")
public class CsAgentController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsAgentService csAgentService;
	@Autowired
	private CsAgentMarkService csAgentMarkService;
	
	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsAgent> csAgents = csAgentService.findPage(customer, page, status, keyword);
		model.addAttribute("csAgents", csAgents);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/agent/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/agent/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsAgent csAgent = csAgentService.findInfoById(id);
		List<CsAgentMark> csAgentMarks = csAgentMarkService.findByAgentId(id);
		model.addAttribute("csAgent", csAgent);
		model.addAttribute("csAgentMarks", csAgentMarks);
		return "cs/agent/info";
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletResponse response, @PathVariable Integer id) {
		csAgentService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletResponse response, @PathVariable Integer id) {
		csAgentService.finish(id);
	}
	
	@RequestMapping(value = "{id}/output", method = RequestMethod.POST)
	public void output(HttpServletResponse response, @PathVariable Integer id) {
		csAgentService.output(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csAgentService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csAgentId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsAgentMark csAgentMark = csAgentMarkService.create(customer, csAgentId, content);
    	model.addAttribute("csAgentMark", csAgentMark);
        return "cs/agent/mark";
    }
	
}
