package com.comdosoft.financial.manage.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsAgentMark;
import com.comdosoft.financial.manage.service.cs.CsAgentMarkService;
import com.comdosoft.financial.manage.service.cs.CsAgentService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/agent")
public class CsAgentController {

	@Autowired
	private CsAgentService csAgentService;
	@Autowired
	private CsAgentMarkService csAgentMarkService;
	
	private void findPage(Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsAgent> csAgents = csAgentService.findPage(page, status, keyword);
		model.addAttribute("csAgents", csAgents);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Integer page, Byte status, String keyword, Model model) {
		findPage(page, status, keyword, model);
		return "cs/agent/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(Integer page, Byte status, String keyword, Model model) {
		findPage(page, status, keyword, model);
		return "cs/agent/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsAgent csAgent = csAgentService.findById(id);
		List<CsAgentMark> csAgentMarks = csAgentMarkService.findByAgentId(id);
		model.addAttribute("csAgent", csAgent);
		model.addAttribute("csAgentMarks", csAgentMarks);
		return "cs/agent/info";
	}
	
}
