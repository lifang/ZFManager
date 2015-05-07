package com.comdosoft.financial.manage.controller.task;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.domain.zhangfu.AgentJoin;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.service.AgentJoinService;
import com.comdosoft.financial.manage.service.RecordOperateService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.task.IntentionService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/task/agentjoin")
public class AgentJoinController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private AgentJoinService agentJoinService;

	@Autowired
	private IntentionService intentionService;

	@Autowired
	private RecordOperateService recordOperateService;

	@RequestMapping(value = "findAngetJoin", method = RequestMethod.POST)
	public String findAgentJoinByType(HttpServletRequest request, Integer page,
			Byte statu, String keys, Model model) {
		pageInfoByxx(request, page, statu, keys, model);
		return "task/intentions/page";
	}
	
	@RequestMapping(value = "list" , method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page,
			Byte statu, String keys, Model model) {
		pageInfoByxx(request, page, statu, keys, model);
        model.addAttribute("itentype_agent", 2);
		return "task/intentions/list";
	}

	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	@ResponseBody
	public void dispatch(HttpServletRequest request, String ids,
			Integer customerId, String customerName) {
		agentJoinService.dispatch(ids, customerId, customerName);
		if (!"".equals(ids.trim())) {
			String[] idd = ids.split(",");
			for (String id : idd) {
				operationRefundContent(request, "分派", Integer.valueOf(id));
			}
		}
	}

	private String content = "name执行了A page的button操作，操作记录的id是 nowId";

	public void operationRefundContent(HttpServletRequest request,
			String button, int id) {
		Customer customer = sessionService.getLoginInfo(request);
		String contentNew = content.replace("name", customer.getName())
				.replace("A page", "购买意向页面").replace("button", button)
				.replace("nowId", String.valueOf(id));
		recordOperateService.saveOperateRecord(customer.getId(),
				customer.getName(), customer.getTypes(),
				OperateRecord.TYPES_BUY_INTENTION, contentNew, id);

	}

	private void pageInfoByxx(HttpServletRequest request, Integer page,
			Byte statu, String keys, Model model) {
		if (page == null) {
            page = 1;
        }
        if (statu != null && statu == 0) {
        	statu = null;
        }
		Customer customer = sessionService.getLoginInfo(request);
		Page<AgentJoin> pageAgent = agentJoinService.findPage(customer.getId(),
				page, statu, keys); 
		model.addAttribute("intentions", pageAgent); 
	}

	@RequestMapping(value = "upstatus", method = RequestMethod.POST)
	@ResponseBody
	public int upStatus(HttpServletRequest request, Integer id, Integer status,
			Model model) {
		int a = agentJoinService.upStatus(id, status);
		String button = null;
		if (status == 2) {
			button = "标记为处理中";
		} else if (status == 3) {
			button = "标记为已处理";
		} else {
			return a;
		}
		operationRefundContent(request, button, id);
		return a;
	}

	@RequestMapping(value = "{id}/getInfo", method = RequestMethod.GET)
	public String getInfo(@PathVariable Integer id, Model model) {
		AgentJoin tinfo = agentJoinService.findAgentById(id);
		model.addAttribute("one", tinfo);
		List<Mark> marks = intentionService.getMark(id);
		model.addAttribute("marks", marks);
		return "task/intentions/info";
	}

}
