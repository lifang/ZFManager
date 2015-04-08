package com.comdosoft.financial.manage.controller.task;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.task.StockManageService;

/**
 * 运营中心  任务  售后库存管理
 * @author yyb
 *
 */
@Controller
@RequestMapping("/task/stockManage")
public class StockManageController {
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private StockManageService stockManageService;
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(){
		return "task/stockManage/stockManage";
	}
	
	
	@RequestMapping(value="showInfo",method=RequestMethod.POST)
	@ResponseBody
	public Response showInfo(String account,HttpServletRequest request){
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= stockManageService.showInfo(account, customer.getId());
		response.setCode(Integer.parseInt(map.get("resultCode").toString()));
		response.setMessage(map.get("resultInfo").toString());
		response.setResult(account);
		return response;
	}
	
	/**
	 * 检测终端号是否存在
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value="checkAccount",method=RequestMethod.POST)
	@ResponseBody
	public Response checkAccount(String account,HttpServletRequest request){
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= stockManageService.checkAccount(account, customer.getId());
		response.setCode(Integer.parseInt(map.get("resultCode").toString()));
		response.setMessage(map.get("resultInfo").toString());
		return response;
	}
	
	/**
	 * 将终端退回售后库
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value="toAfterSaleStock",method=RequestMethod.POST)
	@ResponseBody
	public Response toAfterSaleStock(String account,HttpServletRequest request){
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= stockManageService.toAfterSaleStock(account, customer.getId());
		response.setCode(Integer.parseInt(map.get("resultCode").toString()));
		response.setMessage(map.get("resultInfo").toString());
		return response;
	}
	/**
	 * 将终端放入正常库
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value="toNormalStock",method=RequestMethod.POST)
	@ResponseBody
	public Response toNormalStock(String account,HttpServletRequest request){
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= stockManageService.toNormalStock(account, customer.getId());
		response.setCode(Integer.parseInt(map.get("resultCode").toString()));
		response.setMessage(map.get("resultInfo").toString());
		return response;
	}
	
	/**
	 * 报废
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value="breakDown",method=RequestMethod.POST)
	@ResponseBody
	public Response breakDown(String account,HttpServletRequest request){
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= stockManageService.breakDown(account, customer.getId());
		response.setCode(Integer.parseInt(map.get("resultCode").toString()));
		response.setMessage(map.get("resultInfo").toString());
		return response;
	}
}
