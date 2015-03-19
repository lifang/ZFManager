package com.comdosoft.financial.manage.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerAddressService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order")
public class UserOrderController {
	
	@Autowired
	private OrderService orderService ;
	@Autowired
	private FactoryService factoryService ;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CityService cityService;
	
	@RequestMapping(value="/user/list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys,Integer factoryId, Model model){
		List<Byte> types=new ArrayList<Byte>();
		types.add((byte) 1);
		types.add((byte) 2);
		findPage(page, status, keys,factoryId, model,types);
		return "order/user/list";
	}
	
	@RequestMapping(value="/user/page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys,Integer factoryId, Model model){
		List<Byte> types=new ArrayList<Byte>();
		types.add((byte) 1);
		types.add((byte) 2);
		findPage(page, status, keys,factoryId, model,types);
		return "order/user/pageOrder";
	}
	
	
	@RequestMapping(value="/agent/list",method=RequestMethod.GET)
	public String listAgent(Integer page, Byte status, String keys,Integer factoryId, Model model){
		List<Byte> types=new ArrayList<Byte>();
		types.add((byte) 3);
		types.add((byte) 4);
		findPage(page, status, keys,factoryId, model,types);
		return "order/user/listAgent";
	}
	
	@RequestMapping(value="/agent/page",method=RequestMethod.GET)
	public String pageAgent(Integer page, Byte status, String keys,Integer factoryId, Model model){
		List<Byte> types=new ArrayList<Byte>();
		types.add((byte) 3);
		types.add((byte) 4);
		findPage(page, status, keys,factoryId, model,types);
		return "order/user/pageOrderAgent";
	}
	@RequestMapping(value="/batch/list",method=RequestMethod.GET)
	public String listBatch(Integer page, Byte status, String keys,Integer factoryId, Model model){
		List<Byte> types=new ArrayList<Byte>();
		types.add((byte) 5);
		findPage(page, status, keys,factoryId, model,types);
		return "order/user/listBatch";
	}
	
	@RequestMapping(value="/batch/page",method=RequestMethod.GET)
	public String pageBatch(Integer page, Byte status, String keys,Integer factoryId, Model model){
		List<Byte> types=new ArrayList<Byte>();
		types.add((byte) 5);
		findPage(page, status, keys,factoryId, model,types);
		return "order/user/pageOrderBatch";
	}
	
	private void findPage(Integer page, Byte status, String keys,Integer factoryId, Model model,List<Byte> types){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Order> orders = orderService.findPages(page, status, keys,factoryId,types);
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
    public String createGet(HttpServletRequest request,Model model){
    	Customer customer = sessionService.getLoginInfo(request);
    	List<CustomerAddress> selectCustomerAddress = customerAddressService.selectCustomerAddress(customer.getId());
    	List<City> cities = cityService.cities(0);
    	model.addAttribute("customerAddresses", selectCustomerAddress);
    	model.addAttribute("cities", cities);
        return "order/user/create";
    }
}
