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
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsLeaseService;
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
	@Autowired
	private CsLeaseService csLeaseService;

	private void findPage(Customer customer, Integer page, Byte status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsReturn> csReturns = csReturnService.findPage(customer, page, status, null != keyword ? keyword.trim() : keyword);
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
	public void handle(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csReturnService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csReturnService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csReturnService.finish(id);
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "{id}/confirm", method = RequestMethod.POST)
	@ResponseBody
	public Response confirmReturn(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id, 
			CsReceiverAddress csReceiverAddress) throws Exception {
		Response response1=new Response();
		response1.setCode(Response.SUCCESS_CODE);
		try{
			Customer customer = sessionService.getLoginInfo(request);
			response1=csReturnService.confirm(id, csReceiverAddress, customer);
		}catch(Exception ex){
			ex.printStackTrace();
			response1.setCode(Response.ERROR_CODE);
			response1.setMessage(ex.getMessage());
		}finally{
			return response1;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "{id}/createRefund", method = RequestMethod.POST)
	@ResponseBody
	public Response createRefund(HttpServletRequest request, HttpServletResponse response,  @PathVariable Integer id) throws Exception{
		Response response1=new Response();
		Customer customer = sessionService.getLoginInfo(request);
		try{
		response1=csReturnService.createRefund(id, customer);
		}catch(Exception ex){
			ex.printStackTrace();
			response1.setCode(Response.ERROR_CODE);
			response1.setMessage(ex.getMessage());
		}finally{
			return response1;
		}
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csReturnService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "{id}/mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, @PathVariable Integer id, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsReturnMark csReturnMark = csReturnService.createMark(customer, id, content);
    	model.addAttribute("mark", csReturnMark);
        return "cs/mark";
    }
	
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String goinfo(Integer id,Integer type, Model model) {
		if(type.equals(1)){
			CsReturn csReturn = csReturnService.findInfoById(id);
			List<CsReturnMark> csReturnMarks = csReturnService.findMarksByCsReturnId(id);
			model.addAttribute("csReturn", csReturn);
			model.addAttribute("csReturnMarks", csReturnMarks);
			if (null != csReturn.getCsCencelId() && csReturn.getCsCencelId() > 0) {
				List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
				model.addAttribute("materials", materials);
			}
			return "cs/return/info";
		}else if(type.equals(2)){
			CsLeaseReturn csLease = csLeaseService.findInfoById(id);
			List<CsLeaseReturnMark> csLeaseMarks = csLeaseService.findMarksByCsLeaseReturnId(id);
			model.addAttribute("csLease", csLease);
			model.addAttribute("csLeaseMarks", csLeaseMarks);
			csLeaseService.calculateLease(model, csLease, false);
			if (null != csLease.getCsCencelId() && csLease.getCsCencelId() > 0) {
				List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
				model.addAttribute("materials", materials);
			}
			return "cs/lease/info";
		}
		return null;
	}
	
}
