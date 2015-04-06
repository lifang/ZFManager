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
import com.comdosoft.financial.manage.domain.zhangfu.CsRepair;
import com.comdosoft.financial.manage.domain.zhangfu.CsRepairMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsRepairService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/repair")
public class CsRepairController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsRepairService csRepairService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsRepair> csRepairs = csRepairService.findPage(customer, page, status, keyword);
		model.addAttribute("csRepairs", csRepairs);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/repair/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Byte status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/repair/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsRepair csRepair = csRepairService.findInfoById(id);
		List<CsRepairMark> csRepairMarks = csRepairService.findMarksByCsRepairId(id);
		model.addAttribute("csRepair", csRepair);
		model.addAttribute("csRepairMarks", csRepairMarks);
		return "cs/repair/info";
	}
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletResponse response, @PathVariable Integer id) {
		csRepairService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletResponse response, @PathVariable Integer id) {
		csRepairService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletResponse response, @PathVariable Integer id) {
		csRepairService.finish(id);
	}
	
	@RequestMapping(value = "{id}/addPay", method = RequestMethod.POST)
	public void addPay(HttpServletResponse response, @PathVariable Integer id, Byte payType) {
		csRepairService.addPay(id, payType);
	}
	
	@RequestMapping(value = "{id}/updatePay", method = RequestMethod.POST)
	public void updatePay(HttpServletResponse response, @PathVariable Integer id, Integer repairPrice) {
		csRepairService.updatePay(id, repairPrice);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csRepairService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, Integer csRepairId, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsRepairMark csRepairMark = csRepairService.createMark(customer, csRepairId, content);
    	model.addAttribute("mark", csRepairMark);
        return "cs/mark";
    }
	
	@RequestMapping(value = "bill/edit", method = RequestMethod.GET)
	public String editBill(HttpServletRequest request, Model model) {
        return "cs/repair/bill";
    }
	
	@RequestMapping(value = "bill/create", method = RequestMethod.POST)
	public void createBill(CsReceiverAddress csReceiverAddress,
			String terminalNum, Integer repairPrice, String description, 
			HttpServletRequest request, HttpServletResponse response) {
		Customer customer = sessionService.getLoginInfo(request);
		csRepairService.createBill(customer, csReceiverAddress, terminalNum, repairPrice, description);
	}
}
