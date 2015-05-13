package com.comdosoft.financial.manage.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerAddressService;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order")
public class OrderUserController extends BaseController{

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
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/user/list")
	public String list(Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 1);
		types.add((byte) 2);
		findPage(page, status, keys, factoryId, model, types,null);
		return "order/user/list";
	}

	@RequestMapping(value = "/user/page")
	public String page(Integer page, Byte status, String keys,
			Integer factoryId, Integer pattern ,Model model) {
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 1);
		types.add((byte) 2);
		findPage(page, status, keys, factoryId, model, types ,pattern);
		return "order/user/pageOrder";
	}

	private void findPage(Integer page, Byte status, String keys,
			Integer factoryId, Model model, List<Byte> types,Integer pattern) {
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Order> orders = orderService.findPages(page, status, keys,
					factoryId, types, pattern);
		List<Factory> findCheckedFactories = factoryService
				.findCheckedFactories();
		model.addAttribute("factories", findCheckedFactories);
		model.addAttribute("orders", orders);
	}

	@RequestMapping(value = "/user/{id}/info")
	public String info(@PathVariable Integer id, Model model) {
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		return "order/user/info";
	}

	@RequestMapping(value = "/user/create")
	public String createGet(HttpServletRequest request, Model model,
			Integer goodId, Integer quantity, Integer payChannelId, Byte type) {
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
		return "order/user/create";
	}

	@RequestMapping(value = "/user/createSure")
	public String createSureGet(HttpServletRequest request, Model model,
			Integer goodId, Integer quantity, String comment,
			String invoiceInfo, Integer customerAddressId, Integer invoiceType,
			Boolean needInvoice, int type, Integer payChannelId,
			Integer customerId) throws Exception {
		Customer customer = sessionService.getLoginInfo(request);
		Integer id=orderService
				.save(customer,customerId, goodId, quantity, comment, invoiceInfo,
						customerAddressId, invoiceType, needInvoice, type,
						payChannelId,null);
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 1);
		types.add((byte) 2);
		findPage(1, null, null, null, model, types,null);
		saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserCreate, OperateAction.createSure, id);
		return "order/user/list";

	}

	@RequestMapping(value = "/user/{id}/save")
	public String save(HttpServletRequest request,@PathVariable Integer id, Byte status,
			Float actualPrice, Model model) {
		orderService.save(id, status,actualPrice, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		if(null!=actualPrice){
			saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserList,OperateAction.updatePrice, id);
		}
		if(null!=status){
			saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserList,OperateAction.updateStatus, id);
		}
		return "order/user/pageRowOrder";
	}

	@RequestMapping(value = "/user/info/{id}/save")
	public String saveInfo(HttpServletRequest request,
			@PathVariable Integer id, Byte status, Float actualPrice,
			Model model) {
		orderService.save(id, status, actualPrice, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		if (null != actualPrice) {
			saveOperateRecord(request, OperateType.orderUserType,
					OperatePage.orderUserInfo, OperateAction.updatePrice, id);
		}
		if (null != status) {
			saveOperateRecord(request, OperateType.orderUserType,
					OperatePage.orderUserInfo, OperateAction.updateStatus, id);
		}
		return "order/user/infoUp";
	}

	@RequestMapping(value = "/user/{id}/cancel")
	public String cancle(HttpServletRequest request,@PathVariable Integer id, Model model) {
		orderService.save(id, (byte) 5, null, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserList, OperateAction.cancel, id);
		return "order/user/pageRowOrder";
	}

	@RequestMapping(value = "/user/info/{id}/cancel")
	public String cancleInfo(HttpServletRequest request,@PathVariable Integer id, Model model) {
		orderService.save(id, (byte) 5, null, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserInfo, OperateAction.cancel, id);
		return "order/user/infoUp";
	}
}
