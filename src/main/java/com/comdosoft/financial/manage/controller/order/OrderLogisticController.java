/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月22日 下午10:10:33
 */
package com.comdosoft.financial.manage.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.OrderLogisticService;
import com.comdosoft.financial.manage.service.OrderService;

@Controller
@RequestMapping("/order/logistic")
public class OrderLogisticController {

	@Autowired
	private OrderLogisticService orderLogisticService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="create",method = RequestMethod.GET)
	public String createGet(HttpServletRequest request,Integer orderId,String logisticsName,String logisticsNumber,Model model){
		orderService.save(orderId, (byte)3, null, null);
		orderLogisticService.insert(orderId, logisticsName, logisticsNumber);
		Order order=orderService.findOrderInfo(orderId);
		model.addAttribute("order", order);
        return "order/user/pageRowOrder";
	}
}
