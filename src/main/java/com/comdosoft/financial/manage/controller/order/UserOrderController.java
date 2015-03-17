package com.comdosoft.financial.manage.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order/user")
public class UserOrderController {
	
	@Autowired
	private OrderService orderService ;
	@Autowired
	private FactoryService factoryService ;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys,Integer factoryId, Model model){
		findPage(page, status, keys,factoryId, model);
		return "order/user/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys,Integer factoryId, Model model){
		findPage(page, status, keys,factoryId, model);
		return "order/user/pageOrder";
	}
	
	private void findPage(Integer page, Byte status, String keys,Integer factoryId, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Order> orders = orderService.findPages(page, status, keys,factoryId);
		List<Factory> findCheckedFactories = factoryService.findCheckedFactories();
		model.addAttribute("factories",findCheckedFactories);
		model.addAttribute("orders", orders);
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
		Order order=orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		return "order/user/info";
	}

    @RequestMapping(value="create",method = RequestMethod.GET)
    public String createGet(){
        return "order/user/create";
    }
}
