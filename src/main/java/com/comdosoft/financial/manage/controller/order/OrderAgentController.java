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
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerAddressService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order")
public class OrderAgentController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private FactoryService factoryService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CityService cityService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private PayChannelService payChannelService;
	

	@RequestMapping(value = "/agent/list", method = RequestMethod.GET)
	public String listAgent(Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 3);
		types.add((byte) 4);
		findPage(page, status, keys, factoryId, model, types);
		return "order/agent/list";
	}

	@RequestMapping(value = "/agent/page", method = RequestMethod.GET)
	public String pageAgent(Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 3);
		types.add((byte) 4);
		findPage(page, status, keys, factoryId, model, types);
		return "order/agent/page";
	}


	private void findPage(Integer page, Byte status, String keys,
			Integer factoryId, Model model, List<Byte> types) {
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Order> orders = orderService.findPages(page, status, keys,
				factoryId, types);
		List<Factory> findCheckedFactories = factoryService
				.findCheckedFactories();
		model.addAttribute("factories", findCheckedFactories);
		model.addAttribute("orders", orders);
	}

	@RequestMapping(value = "/agent/{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		return "order/agent/info";
	}

	@RequestMapping(value = "/agent/create", method = RequestMethod.GET)
	public String createGet(HttpServletRequest request, Model model,
			Integer goodId, Integer quantity,Integer payChannelId,Byte type) {
		List<CustomerAddress> selectCustomerAddress = null;
		List<City> cities = cityService.cities(0);
		Good good = goodService.findGoodInfo(goodId);
		PayChannel payChannel = payChannelService.findChannelInfo(payChannelId);
		model.addAttribute("customerAddresses", selectCustomerAddress);
		model.addAttribute("cities", cities);
		model.addAttribute("good", good);
		model.addAttribute("quantity", quantity);
		model.addAttribute("payChannel", payChannel);
		model.addAttribute("type", type);
		return "order/agent/create";
	}
	
	@RequestMapping(value = "/agent/{orderId}/createAgain", method = RequestMethod.GET)
	public String createAgainGet(HttpServletRequest request, Model model,
			@PathVariable Integer orderId) {
		Order order = orderService.findOrderInfo(orderId);
		List<CustomerAddress> selectCustomerAddress = customerAddressService
				.selectCustomerAddress(order.getCustomerId());
		List<City> cities = cityService.cities(0);
		model.addAttribute("customerAddresses", selectCustomerAddress);
		model.addAttribute("cities", cities);
		model.addAttribute("order", order);
		model.addAttribute("type", order.getTypes());
		return "order/agent/create";
	}
	
	@RequestMapping(value = "/agent/createSure", method = RequestMethod.GET)
	public String createSureGet(HttpServletRequest request, Model model,
			Integer goodId, Integer quantity, String comment,
			String invoiceInfo, Integer customerAddressId, Integer invoiceType,
			Boolean needInvoice, int type, Integer payChannelId,
			Integer customerId,Integer agentCustomerId) {
		Customer customer = sessionService.getLoginInfo(request);
		Integer id= orderService
				.save(customer,customerId, goodId, quantity, comment, invoiceInfo,
						customerAddressId, invoiceType, needInvoice, type,
						payChannelId,agentCustomerId);
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 3);
		types.add((byte) 4);
		findPage(1, null, null, null, model, types);
		saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentCreate, OperateAction.createSure, id);
		return "order/agent/list";
	}
	
	@RequestMapping(value = "/agent/createSureAgain", method = RequestMethod.GET)
	public String createSureAgainGet(HttpServletRequest request, Model model,
			Integer orderId, String goodQuantity, String comment,
			String invoiceInfo, Integer customerAddressId, Integer invoiceType,
			Boolean needInvoice, int type, Integer customerId,Integer agentCustomerId) throws Exception {
		Customer customer = sessionService.getLoginInfo(request);
		Integer id= orderService.save(customer,customerId, orderId, goodQuantity, comment,
		invoiceInfo, customerAddressId, invoiceType, needInvoice, type,agentCustomerId);
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 3);
		types.add((byte) 4);
		findPage(1, null, null, null, model, types);
		saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentCreate, OperateAction.createSure, id);
		return "order/agent/list";
	}

	@RequestMapping(value = "/agent/{id}/save", method = RequestMethod.GET)
	public String save(HttpServletRequest request,@PathVariable Integer id, Byte status,
			Float actualPrice, Model model) {
		orderService.save(id, status, actualPrice, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		if(null!=status){
			saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentList, OperateAction.updateStatus, id);
		}
		if(null!=actualPrice){
			saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentList, OperateAction.updatePrice, id);
		}
		
		return "order/agent/row";
	}
	
	@RequestMapping(value = "/agent/info/{id}/save", method = RequestMethod.GET)
	public String saveInfo(HttpServletRequest request,@PathVariable Integer id, Byte status,
			Float actualPrice, Model model) {
		orderService.save(id, status, actualPrice, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		if(null!=status){
			saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentInfo, OperateAction.updateStatus, id);
		}
		if(null!=actualPrice){
			saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentInfo, OperateAction.updatePrice, id);
		}
		return "order/agent/infoUp";
	}

	@RequestMapping(value = "/agent/{id}/cancel", method = RequestMethod.GET)
	public String cancle(HttpServletRequest request,@PathVariable Integer id, Model model) {
		orderService.save(id, (byte) 5, null, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentList, OperateAction.cancel, id);
		return "order/agent/row";
	}
	
	@RequestMapping(value = "/agent/info/{id}/cancel", method = RequestMethod.GET)
	public String cancleInfo(HttpServletRequest request,@PathVariable Integer id, Model model) {
		orderService.save(id, (byte) 5, null, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderAgentType, OperatePage.orderAgentInfo, OperateAction.cancel, id);
		return "order/agent/infoUp";
	}
}
