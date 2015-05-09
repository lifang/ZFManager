package com.comdosoft.financial.manage.controller.cs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsCancelMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCancelService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.utils.HttpFile;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/cancel")
public class CsCancelController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsCancelService csCancelService;
	@Autowired
	private CsCommonService csCommonService;
	@Value("${filePath}")
	private String filePath;
	
	private void findPage(Customer customer, Integer page, Integer status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsCancel> csCancels = csCancelService.findPage(customer, page, status, null != keyword ? keyword.trim() : keyword);
		model.addAttribute("csCancels", csCancels);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Integer status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/cancel/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Integer status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/cancel/table";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) throws IOException, ClassNotFoundException {
		try {
			CsCancel csCancel = csCancelService.findInfoById(id);
			List<CsCancelMark> csCancelMarks = csCancelService.findMarksByCsCancelId(id);
			model.addAttribute("csCancel", csCancel);
			model.addAttribute("csCancelMarks", csCancelMarks);
			if (null != csCancel.getId() && csCancel.getId() > 0) {
				List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
				model.addAttribute("materials", materials);
			}
			
			String templateStr=csCancel.getTempleteInfoXml();
			String warPath="csCancel"+csCancel.getTerminal().getSerialNum()+csCancel.getPayChannel().getId();
			//String warPath="2";
			if(templateStr.length()>0){
				Map<String, Object> resultMap = csCancelService.makeWar(templateStr, warPath);
				if(Integer.parseInt(resultMap.get("resultCode").toString())==Response.SUCCESS_CODE){
					model.addAttribute("warPath", filePath+"zip/terminal/"+warPath+".zip");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return "cs/cancel/info";
		}
	}
	
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csCancelService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csCancelService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csCancelService.finish(id);
	}
	
	@RequestMapping(value = "{id}/resubmit", method = RequestMethod.POST)
	public void resubmit(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csCancelService.resubmit(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csCancelService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "{id}/mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, @PathVariable Integer id, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsCancelMark csCancelMark = csCancelService.createMark(customer, id, content);
    	model.addAttribute("mark", csCancelMark);
        return "cs/mark";
    }
	
}
