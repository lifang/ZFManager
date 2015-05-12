package com.comdosoft.financial.manage.controller.cs;

import java.util.ArrayList;
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
import com.comdosoft.financial.manage.service.cs.CsChangeService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/agent")
public class CsAgentController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsAgentService csAgentService;
	@Autowired
	private CsChangeService csChangeService;
	
	private static final String LOGIN_SESSION_KEY = "__LOGIN_KEY__";
	
	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsAgent> csAgents = csAgentService.findPage(customer, page, status, null != keyword ? keyword.trim() : keyword);
		model.addAttribute("csAgents", csAgents);
		model.addAttribute("payChannelList",csChangeService.getPayChannelList());
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
	public Response output(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id,
			String terminalList,Integer payChannelId,String checkCodesStr) {
		String[] terminalNums = terminalList.split(",");
		
		if(!checkCodesStr.trim().equals("")){
			String[] checkCodes=checkCodesStr.split(",");
			if(checkCodes.length!=terminalNums.length){
				return Response.getError("输入的终端数和激活码数不一致");
			}
		}
		
		Customer customer = (Customer)request.getSession().getAttribute(LOGIN_SESSION_KEY);//获取登录信息
		List<String> terminals = new ArrayList<String>();
		List<String> invalidTermianls = new ArrayList<String>();
		int cnt = 0;
		for(int i=0;i<terminalNums.length;i++){
			String t=terminalNums[i];
			
			Terminal terminal = csAgentService.findTerminal(t);
			//激活码
			if(!checkCodesStr.trim().equals("")){
				String[] checkCodes=checkCodesStr.split(",");
				if(terminal != null){
					terminal.setPayChannelId(payChannelId);
					terminal.setReserver2(checkCodes[i]);
					csAgentService.output(id, terminal);
					terminals.add(t);
					cnt ++;
				}else{
					invalidTermianls.add(t);
				}
			}else{
				if(terminal != null){
					terminal.setPayChannelId(payChannelId);
					csAgentService.output(id, terminal);
					terminals.add(t);
					cnt ++;
				}else{
					invalidTermianls.add(t);
				}
			}
		}
		
//		for(String t : terminalNums){
//			Terminal terminal = csAgentService.findTerminal(t);
//			if(terminal != null){
//				terminal.setPayChannelId(payChannelId);
//				csAgentService.output(id, terminal);
//				terminals.add(t);
//				cnt ++;
//			}else{
//				invalidTermianls.add(t);
//			}
//		}
		
		String temp = terminals.toString().replaceAll("\\[|\\]", "");
		if(!"".equals(temp) && cnt < terminalNums.length){
			csAgentService.csOutput(id, cnt, customer.getId(), customer.getName(), temp);
			return Response.getError(invalidTermianls.toString()+"无法换货出库，"+temp+"已添加换货出库记录成功");
		}else if(!"".equals(temp) && cnt == terminalNums.length){
			csAgentService.csOutput(id, cnt, customer.getId(), customer.getName(), terminalList);
			return Response.getSuccess("成功");
		}else{
			return Response.getError(invalidTermianls.toString()+"无法换货出库");
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
