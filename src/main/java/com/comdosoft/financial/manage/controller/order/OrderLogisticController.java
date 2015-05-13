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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.OrderLogisticService;
import com.comdosoft.financial.manage.service.OrderService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
@RequestMapping("/order/logistic")
public class OrderLogisticController extends BaseController {

	@Autowired
	private OrderLogisticService orderLogisticService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SessionService sessionService;

	public Boolean deliver(HttpServletRequest request, Integer orderId,
			String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2)  {
		Customer customer = sessionService.getLoginInfo(request);
		try {
			orderLogisticService.deliver(customer, orderId, terminalSerialNum,
					logisticsName, logisticsNumber, goodQuantity, reserver2);
		} catch (Exception e) {
			model.addAttribute("response", Response.getError(e.getMessage()));
			e.printStackTrace();
			return false;
		}
		Order order = orderService.findOrderInfo(orderId);
		model.addAttribute("order", order);
		return true;
	}
	@RequestMapping(value = "check", method = {RequestMethod.POST})
	public String checkOutStorage(Integer orderId, Model model){
		if(orderLogisticService.judgeDeliver(orderId)!=null){
			model.addAttribute("response", Response.getError("相关发货单已生成，请勿重复发货，请至任务-->出库中处理该出库单"));
		}else{
			model.addAttribute("response", Response.getSuccess("ok"));
		}
		return "order/error";
		
	}
	@RequestMapping(value = "create", method = {RequestMethod.GET,RequestMethod.POST})
	public String createGet(HttpServletRequest request, Integer orderId,
			String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2) {
		if(!deliver(request, orderId, terminalSerialNum, logisticsName,
				logisticsNumber, model, goodQuantity, reserver2)){
			return "order/error";
		}
		saveOperateRecord(request, OperateType.orderUserType,
				OperatePage.orderUserList, OperateAction.deliver, orderId);
		return "order/user/pageRowOrder";
	}

	@RequestMapping(value = "/info/create", method = {RequestMethod.GET,RequestMethod.POST})
	public String createInfoGet(HttpServletRequest request, Integer orderId,
			String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2) {
		if(!deliver(request, orderId, terminalSerialNum, logisticsName,
				logisticsNumber, model, goodQuantity, reserver2)){
			return "order/error";
		}
		saveOperateRecord(request, OperateType.orderUserType,
				OperatePage.orderUserInfo, OperateAction.deliver, orderId);
		return "order/user/infoUp";
	}

	@RequestMapping(value = "/agent/create", method = {RequestMethod.GET,RequestMethod.POST})
	public String createAgentGet(HttpServletRequest request, Integer orderId,
			String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2) {
		if(!deliver(request, orderId, terminalSerialNum, logisticsName,
				logisticsNumber, model, goodQuantity, reserver2)){
			return "order/error";
		}
		saveOperateRecord(request, OperateType.orderAgentType,
				OperatePage.orderAgentList, OperateAction.deliver, orderId);
		return "order/agent/row";
	}

	@RequestMapping(value = "/agent/info/create", method = {RequestMethod.GET,RequestMethod.POST})
	public String createAgentInfoGet(HttpServletRequest request,
			Integer orderId, String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2) {
		if(!deliver(request, orderId, terminalSerialNum, logisticsName,
				logisticsNumber, model, goodQuantity, reserver2)){
			return "order/error";
		}
		saveOperateRecord(request, OperateType.orderAgentType,
				OperatePage.orderAgentInfo, OperateAction.deliver, orderId);
		return "order/agent/infoUp";
	}

	@RequestMapping(value = "/batch/create", method = {RequestMethod.GET,RequestMethod.POST})
	public String createBatchGet(HttpServletRequest request, Integer orderId,
			String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2) {
		if(!deliver(request, orderId, terminalSerialNum, logisticsName,
				logisticsNumber, model, goodQuantity, reserver2)){
			return "order/error";
		}
		saveOperateRecord(request, OperateType.orderBatchType,
				OperatePage.orderBatchList, OperateAction.deliver, orderId);
		return "order/batch/row";
	}

	@RequestMapping(value = "/batch/info/create", method = {RequestMethod.GET,RequestMethod.POST})
	public String createBatchInfoGet(HttpServletRequest request,
			Integer orderId, String terminalSerialNum, String logisticsName,
			String logisticsNumber, Model model, String goodQuantity,
			String reserver2) {
		if(!deliver(request, orderId, terminalSerialNum, logisticsName,
				logisticsNumber, model, goodQuantity, reserver2)){
			return "order/error";
		}
		saveOperateRecord(request, OperateType.orderBatchType,
				OperatePage.orderBatchInfo, OperateAction.deliver, orderId);
		return "order/batch/infoUp";
	}
	@RequestMapping(value = "/info/{id}/showTerminal", method = RequestMethod.POST)
	@ResponseBody
	public Response showTerminal(@PathVariable Integer id,Integer goodId) {
		Terminal t = orderService.getTerminal(id,goodId);
		if(t.getReserver2()!=null && !"".equals(t.getReserver2().trim())){
			return Response.getSuccess(t.getSerialNum()+"(激活码："+t.getReserver2()+")");
		}
		return Response.getSuccess(t.getSerialNum());
	}
}
