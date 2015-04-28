/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2015年3月20日 下午1:31:35
 */
package com.comdosoft.financial.manage.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.OrderPaymentService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
@RequestMapping("/order/payment")
public class OrderPaymentContoller extends BaseController {
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private OrderService orderService;
	
    @RequestMapping(value="/user/create")
    public String createGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.save(orderId, (byte)2, null, (byte)2);
    	Order order=orderService.findOrderInfo(orderId);
		orderPaymentService.insert(orderId, order.getActualPrice(), payType, customer.getId(), customer.getTypes());
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserList,OperateAction.payment, orderId);
        return "order/user/pageRowOrder";
    }
    
    @RequestMapping(value="/user/info/create")
    public String createInfoGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.save(orderId, (byte)2, null, (byte)2);
    	Order order=orderService.findOrderInfo(orderId);
    	orderPaymentService.insert(orderId, order.getActualPrice(), payType, customer.getId(), customer.getTypes());
    	model.addAttribute("order", order);
    	saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserInfo,OperateAction.payment, orderId);
    	return "order/user/infoUp";
    }
    
    @RequestMapping(value="/agent/create")
    public String createAgentGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.save(orderId, (byte)2, null, (byte)2);
    	Order order=orderService.findOrderInfo(orderId);
		orderPaymentService.insert(orderId, order.getActualPrice(), payType, customer.getId(), customer.getTypes());
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentList,OperateAction.payment, orderId);
        return "order/agent/row";
    }
    @RequestMapping(value="/agent/info/create")
    public String createAgentInfoGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.save(orderId, (byte)2, null, (byte)2);
    	Order order=orderService.findOrderInfo(orderId);
    	orderPaymentService.insert(orderId, order.getActualPrice(), payType, customer.getId(), customer.getTypes());
    	model.addAttribute("order", order);
    	saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentInfo,OperateAction.payment, orderId);
    	return "order/agent/infoUp";
    }
    
    @RequestMapping(value="/batch/create/front")
    public String createBatchGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.savePayFront(orderId);
    	Order order=orderService.findOrderInfo(orderId);
    	orderPaymentService.insert(orderId, order.getFrontMoney(), payType, customer.getId(), customer.getTypes());
    	model.addAttribute("order", order);
    	saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList,OperateAction.paymentFront, orderId);
    	return "order/batch/row";
    }
    
    @RequestMapping(value="/batch/create")
    public String createBatchGet(HttpServletRequest request,Model model,Integer orderId,Byte payType,Float payPrice){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderPaymentService.payForBatch(customer, orderId, payType, payPrice);
    	Order order=orderService.findOrderInfo(orderId);
    	model.addAttribute("order", order);
    	saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList,OperateAction.payment, orderId);
    	return "order/batch/row";
    }
    
    @RequestMapping(value="/batch/info/create")
    public String createBatchInfoGet(HttpServletRequest request,Model model,Integer orderId,Byte payType,Float payPrice){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderPaymentService.payForBatch(customer, orderId, payType, payPrice);
    	Order order=orderService.findOrderInfo(orderId);
    	model.addAttribute("order", order);
    	saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchInfo,OperateAction.payment, orderId);
    	return "order/batch/infoUp";
    }
}
