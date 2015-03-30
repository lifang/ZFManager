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
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.OrderPaymentService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
@RequestMapping("/order/payment")
public class OrderPaymentContoller {
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private OrderService orderService;
	
    @RequestMapping(value="/user/create",method = RequestMethod.GET)
    public String createGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.save(orderId, (byte)2, null, (byte)2);
    	Order order=orderService.findOrderInfo(orderId);
		orderPaymentService.insert(orderId, order.getActualPrice(), payType, customer.getId(), customer.getTypes());
		model.addAttribute("order", order);
        return "order/user/pageRowOrder";
    }
    
    @RequestMapping(value="/agent/create",method = RequestMethod.GET)
    public String createAgentGet(HttpServletRequest request,Integer orderId,Byte payType,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderService.save(orderId, (byte)2, null, (byte)2);
    	Order order=orderService.findOrderInfo(orderId);
		orderPaymentService.insert(orderId, order.getActualPrice(), payType, customer.getId(), customer.getTypes());
		model.addAttribute("order", order);
        return "order/agent/row";
    }
}
