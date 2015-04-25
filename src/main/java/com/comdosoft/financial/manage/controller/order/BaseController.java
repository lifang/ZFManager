/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年4月11日 下午6:39:52
 */
package com.comdosoft.financial.manage.controller.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.RecordOperateService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
public class BaseController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private RecordOperateService recordOperateService;
	
	public static class OperateType{
		public static final Integer orderAgentType=1; 
		public static final Integer orderBatchType=2;
		public static final Integer orderUserType=3;
	}
	
	public static class OperatePage{
		public static final String orderAgentList="代理商代购订单列表"; 
		public static final String orderBatchList="代理商批购订单列表"; 
		public static final String orderUserList="用户订单列表"; 
		
		public static final String orderAgentCreate="运营中心-订单--代理商代购--确认订单"; 
		public static final String orderBatchCreate="运营中心-订单--代理商批购--确认订单"; 
		public static final String orderUserCreate="运营中心-订单--用户--确认订单";
		
		public static final String orderAgentGoodList="运营中心-订单--代理商代购--商品列表"; 
		public static final String orderBatchGoodList="运营中心-订单--代理商批购--商品列表"; 
		public static final String orderUserGoodList="运营中心-订单--用户--商品列表"; 
		
		public static final String orderAgentGoodDetail="运营中心-订单--代理商代购--商品详情"; 
		public static final String orderBatchGoodDetail="运营中心-订单--代理商批购--商品详情"; 
		public static final String orderUserGoodDetail="运营中心-订单--用户--商品详情";
		
		public static final String orderAgentInfo="运营中心-订单--代理商代购--详情"; 
		public static final String orderBatchInfo="运营中心-订单--代理商批购--详情"; 
		public static final String orderUserInfo="运营中心-订单--用户--详情";
	}
	
	public static class OperateAction{
		public static final String updatePrice="修改价格";
		public static final String updateStatus="修改状态";
		public static final String cancel="取消";
		public static final String createSure="确认订单";
		public static final String payment="增加付款记录";
		public static final String mark="备注";
		public static final String deliver="发货";
		public static final String frontMoney="订金价格";
		public static final String saveCustomer="新增用户";
		public static final String customerAddress="修改用户地址";
		public static final String paymentFront="支付定金";
	}
	
	public int saveOperateRecord(HttpServletRequest request,Integer types,String page,String action,Integer operateTargetId){
		Customer customer = sessionService.getLoginInfo(request);
		StringBuffer content=new StringBuffer();
		content.append(customer.getName() + " 执行了");
		content.append(page + " 界面的 ");
		content.append(action + " 操作, ");
		content.append("操作记录的id是:");
		if (null != operateTargetId) content.append(operateTargetId);
		return recordOperateService.saveOperateRecord(customer.getId(), customer.getName(), customer.getTypes(), types, content.toString(), operateTargetId);
	}
	
	@ExceptionHandler(value = Exception.class)
	public void handleException(Exception e, HttpServletResponse response)
			throws Exception {
		Response error = Response.getError(e.getMessage()!=null?e.getMessage():"^^错误信息为空………");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(error.getCode()+error.getMessage());
	}
}
