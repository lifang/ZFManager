package com.comdosoft.financial.manage.controller.factory;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller("FactoryOrderController")
@RequestMapping("/factory/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private FactoryService factoryService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		Customer c = sessionService.getLoginInfo(request);
		findPage(page, status, keys, model, c.getId());
		return "factory_role/order/list";
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(HttpServletRequest request,Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		Customer c = sessionService.getLoginInfo(request);
		findPage(page, status, keys, model, c.getId());
		return "factory_role/order/pageOrder";
	}

	private void findPage(Integer page, Byte status, String keys, Model model, Integer customerId) {
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Order> orders = orderService.findFactoryPages(page, status, keys, customerId);
		model.addAttribute("orders", orders);
	}

	@RequestMapping(value = "/{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		return "factory_role/order/info";
	}
  
}
