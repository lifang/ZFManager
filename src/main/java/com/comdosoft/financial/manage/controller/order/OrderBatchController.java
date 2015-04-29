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
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order")
public class OrderBatchController extends BaseController {

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

	@RequestMapping(value = "/batch/list")
	public String listBatch(Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 5);
		findPage(page, status, keys, factoryId, model, types);
		return "order/batch/list";
	}

	@RequestMapping(value = "/batch/page")
	public String pageBatch(Integer page, Byte status, String keys,
			Integer factoryId, Model model) {
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 5);
		findPage(page, status, keys, factoryId, model, types);
		return "order/batch/page";
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

	@RequestMapping(value = "/batch/{id}/info")
	public String info(@PathVariable Integer id, Model model) {
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		return "order/batch/info";
	}

	@RequestMapping(value = "/batch/create")
	public String createGet(HttpServletRequest request, Model model,
			Integer goodId, Integer quantity,Integer payChannelId, Byte type) {
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
		return "order/batch/create";
	}
	
	@RequestMapping(value = "/batch/{orderId}/createAgain")
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
		return "order/batch/create";
	}

	@RequestMapping(value = "/batch/createSure")
	public String createSureGet(HttpServletRequest request, Model model,
			Integer goodId, Integer quantity, String comment,
			String invoiceInfo, Integer customerAddressId, Integer invoiceType,
			Boolean needInvoice, int type, Integer payChannelId,
			Integer customerId) throws Exception {
		Customer customer = sessionService.getLoginInfo(request);
		int orderId=orderService
				.save(customer,customerId, goodId, quantity, comment, invoiceInfo,
						customerAddressId, invoiceType, needInvoice, type,
						payChannelId,null);
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 5);
		findPage(1, null, null, null, model, types);
		saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchCreate, OperateAction.createSure, orderId);
		return "order/batch/list";
	}
	
	 /**
		* 再次订购确认订单
		* @throws Exception
		*/
	@RequestMapping(value = "/batch/createSureAgain")
	public String createSureAgainGet(HttpServletRequest request, Model model,
			Integer orderId, String goodQuantity, String comment,
			String invoiceInfo, Integer customerAddressId, Integer invoiceType,
			Boolean needInvoice, int type, Integer customerId) throws Exception {
		Customer customer = sessionService.getLoginInfo(request);
		int orderNewId=orderService.save(customer,customerId, orderId, goodQuantity, comment,
				invoiceInfo, customerAddressId, invoiceType, needInvoice, type,null);
		List<Byte> types = new ArrayList<Byte>();
		types.add((byte) 5);
		findPage(1, null, null, null, model, types);
		saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchCreate, OperateAction.createSure, orderNewId);
		return "order/batch/list";
	}

	@RequestMapping(value = "/batch/{id}/save")
	public String save(HttpServletRequest request,@PathVariable Integer id, Model model, Byte status,
			Float actualPrice,Float frontMoney) {
		orderService.save(id, status, actualPrice, null,frontMoney);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		if(null!=status){
			saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList, OperateAction.updateStatus, id);
		}
		if(null!=actualPrice){
			saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList, OperateAction.updatePrice, id);
		}
		if(null!=frontMoney){
			saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList, OperateAction.frontMoney, id);
		}
		return "order/batch/row";
	}
	
	@RequestMapping(value = "/batch/info/{id}/save")
	public String saveInfo(HttpServletRequest request,@PathVariable Integer id, Model model, Byte status,
			Float actualPrice,Float frontMoney) {
		orderService.save(id, status, actualPrice, null,frontMoney);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		if(null!=status){
			saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchInfo, OperateAction.updateStatus, id);
		}
		if(null!=actualPrice){
			saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchInfo, OperateAction.updatePrice, id);
		}
		if(null!=frontMoney){
			saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchInfo, OperateAction.frontMoney, id);
		}
		return "order/batch/infoUp";
	}

	@RequestMapping(value = "/batch/{id}/cancel")
	public String cancle(HttpServletRequest request,@PathVariable Integer id, Model model) {
		orderService.save(id, (byte) 5, null, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchList, OperateAction.cancel, id);
		return "order/batch/row";
	}
	
	@RequestMapping(value = "/batch/info/{id}/cancel")
	public String cancleInfo(HttpServletRequest request,@PathVariable Integer id, Model model) {
		orderService.save(id, (byte) 5, null, null);
		Order order = orderService.findOrderInfo(id);
		model.addAttribute("order", order);
		saveOperateRecord(request,OperateType.orderBatchType, OperatePage.orderBatchInfo, OperateAction.cancel, id);
		return "order/batch/infoUp";
	}
}
