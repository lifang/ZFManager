package com.comdosoft.financial.manage.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.cs.CsCommonService;

@Controller
@RequestMapping("cs")
public class CsCommonController {

	@Autowired
	private CsCommonService csCommonService;

	@RequestMapping(value = "dispatch", method = RequestMethod.GET)
	public String dispatch(Model model) {
		List<Customer> customers = csCommonService.findDispatchUsers();
		model.addAttribute("customers", customers);
		return "cs/dispatch";
	}
}
