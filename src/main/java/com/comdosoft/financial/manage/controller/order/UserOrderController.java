package com.comdosoft.financial.manage.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order/user")
public class UserOrderController {
	
	@Autowired
	private OrderService orderService ;
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "order/user/list";
	}
	
	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Order> orders = orderService.findPages(page, status, keys);
		model.addAttribute("orders", orders);
	}
	

    @RequestMapping(value="create",method = RequestMethod.GET)
    public String createGet(){
        return "order/user/create";
    }
}
