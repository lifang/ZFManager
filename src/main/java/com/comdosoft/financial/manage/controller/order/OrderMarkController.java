package com.comdosoft.financial.manage.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.OrderMarkService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
@RequestMapping("/order/mark")
public class OrderMarkController extends BaseController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private OrderMarkService orderMarkService;
	@Autowired
	private OrderService orderService;

    @RequestMapping(value="/user/create",method = {RequestMethod.GET,RequestMethod.POST})
    public String createGet(HttpServletRequest request,Integer orderId,String content,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderMarkService.insert(customer.getId(), orderId, content);
    	Order order=orderService.findOrderInfo(orderId);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserList,OperateAction.mark, orderId);
        return "order/user/orderMark";
    }
    
    @RequestMapping(value="/agent/create",method = {RequestMethod.GET,RequestMethod.POST})
    public String createAgentGet(HttpServletRequest request,Integer orderId,String content,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderMarkService.insert(customer.getId(), orderId, content);
    	Order order=orderService.findOrderInfo(orderId);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentList,OperateAction.mark, orderId);
        return "order/agent/orderMark";
    }
    
    @RequestMapping(value="/batch/create",method =  {RequestMethod.GET,RequestMethod.POST})
    public String createBatchGet(HttpServletRequest request,Integer orderId,String content,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderMarkService.insert(customer.getId(), orderId, content);
    	Order order=orderService.findOrderInfo(orderId);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList,OperateAction.mark, orderId);
        return "order/batch/orderMark";
    }
}
