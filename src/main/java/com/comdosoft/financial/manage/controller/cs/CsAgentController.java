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
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsAgentMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsAgentService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/agent")
public class CsAgentController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsAgentService csAgentService;
	
	private static final String LOGIN_SESSION_KEY = "__LOGIN_KEY__";
	
	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsAgent> csAgents = csAgentService.findPage(customer, page, status, null != keyword ? keyword.trim() : keyword);
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
		List<CsAgentMark> csAgentMarks = csAgentService.findMarksByCsAgentId(id);
		model.addAttribute("csAgent", csAgent);
		model.addAttribute("csAgentMarks", csAgentMarks);
		return "cs/agent/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csAgentService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csAgentService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csAgentService.finish(id);
	}
	
	@RequestMapping(value = "{id}/output", method = RequestMethod.POST)
	@ResponseBody
	public Response output(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id, String terminalList) {
		String[] terminalNums = terminalList.split(",");
		Customer customer = (Customer)request.getSession().getAttribute(LOGIN_SESSION_KEY);//获取登录信息
		StringBuilder sb = new StringBuilder("");
		String temp = "";
		StringBuilder sb1 = new StringBuilder("");
		String invalidTermianl = "";//暂存某无效终端
		String tempTerminalList = "";//暂存某无效终端之前的所有终端号
		int cnt = 0;
		for(String t : terminalNums){
			Terminal terminal = csAgentService.findTerminal(t);
			if(terminal != null){
				csAgentService.output(id, terminal);
				sb.append(t+"，");
				cnt ++;
			}else{
				sb1.append(t+"，");
			}
		}
		temp = sb.toString();
		invalidTermianl = sb1.toString();
		if(!"".equals(temp) && cnt < terminalNums.length){
			tempTerminalList = temp.substring(0, temp.lastIndexOf("，"));
			csAgentService.csOutput(id, cnt, customer.getId(), customer.getName(), tempTerminalList);
			return Response.getError(invalidTermianl.substring(0, invalidTermianl.lastIndexOf("，"))+"无法换货出库，"+tempTerminalList+"已添加换货出库记录成功");
		}else if(!"".equals(temp) && cnt == terminalNums.length){
			csAgentService.csOutput(id, cnt, customer.getId(), customer.getName(), terminalList);
			return Response.getSuccess("成功");
		}else{
			return Response.getError(invalidTermianl.substring(0, invalidTermianl.lastIndexOf("，"))+"无法换货出库");
		}
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csAgentService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "{id}/mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, @PathVariable Integer id, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsAgentMark csAgentMark = csAgentService.createMark(customer, id, content);
    	model.addAttribute("mark", csAgentMark);
        return "cs/mark";
    }
	
}
