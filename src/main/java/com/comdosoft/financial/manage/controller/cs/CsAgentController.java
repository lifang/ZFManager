package com.comdosoft.financial.manage.controller.cs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.service.CsAgentService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/agent")
public class CsAgentController {

	private static final Logger LOG = LoggerFactory.getLogger(CsAgentController.class);

	@Autowired
	private CsAgentService csAgentService;
	
	private void findPage(Integer page, Byte status, String keyword, Model model){
		if (page == null) {
			page = 1;
		}
		if (null != status && status < 0) {
			status = null;
		}
		if ("".equals(keyword)) {
			keyword = null;
		}
		Page<CsAgent> csAgents = csAgentService.findPages(page, status, keyword);
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
	
}
