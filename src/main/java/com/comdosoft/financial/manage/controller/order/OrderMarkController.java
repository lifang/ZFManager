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
@RequestMapping("/order/mark/user")
public class OrderMarkController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private OrderMarkService orderMarkService;
	@Autowired
	private OrderService orderService;

    @RequestMapping(value="create",method = RequestMethod.GET)
    public String createGet(HttpServletRequest request,Integer orderId,String content,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	orderMarkService.insert(customer.getId(), orderId, content);
    	Order order=orderService.findOrderInfo(orderId);
		model.addAttribute("order", order);
        return "order/user/orderMark";
    }
}
