package com.comdosoft.financial.manage.controller.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.task.Good;
import com.comdosoft.financial.manage.domain.zhangfu.task.OutStore;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.task.OutStoreService;
import com.comdosoft.financial.manage.utils.page.Page;


@Controller
@RequestMapping("/task/outStore")
public class OutStoreController {
	private Logger LOG = LoggerFactory.getLogger(OutStoreController.class);
	
	@Autowired
	private OutStoreService outStoreService ;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "task/outStore/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "task/outStore/pageOutStore";
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id,Model model){
		List<Good> goods=outStoreService.getGoodInfoInit(id); 
		//收货地址
		Map<String, Object> address=outStoreService.getAddressInit(id);
		model.addAttribute("goods", goods);
		model.addAttribute("address", address);
		model.addAttribute("outStorageId",id);
		model.addAttribute("orderId",outStoreService.getOrderIdByOutStorageId(id));
		model.addAttribute("orderNumber",outStoreService.getOrderNumberByOutStorageId(id));
		model.addAttribute("operater",outStoreService.getOperater(id));
		model.addAttribute("orderDetails",outStoreService.getOrderDetailInfo(id));
		Map<String, Object> map=outStoreService.getWLInfo(id);
		if(null !=map){
			model.addAttribute("wlCompany",map.get("wlName"));
			model.addAttribute("wlNum",map.get("wlNum"));
		}
		//备注记录
		List<Map<String, Object>> remarks=outStoreService.getRemarks(id);
		model.addAttribute("remarks",remarks);
		return "task/outStore/outRecordInfo";
	}
	/**
	 * 添加出库记录信息数据初始化
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{id}/add",method=RequestMethod.GET)
	public String addInfoInitById(@PathVariable Integer id, Model model){
		List<Good> goods=outStoreService.getGoodInfoInit(id); 
		//收货地址
		Map<String, Object> address=outStoreService.getAddressInit(id);
		model.addAttribute("goods", goods);
		model.addAttribute("address", address);
		model.addAttribute("outStorageId",id);
		return "task/outStore/addOutRecord";
	}
	/**
	 * 保存物流信息，商品的终端号码
	 * @param req
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public Response saveTerminalNum(int id,String wlCompany,String wlNum,String terminalNums,
			String quantities,String goodIds,HttpServletRequest request) throws Exception{
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			map= outStoreService.save(id,wlCompany,wlNum,terminalNums,customer.getId(),customer.getTypes());
			response.setCode(Integer.parseInt(map.get("resultCode").toString()));
			response.setMessage(map.get("resultInfo").toString());
			outStoreService.subGoodQuantity(quantities,goodIds);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
		
	}
	/**
	 * 将出库单状态改为取消 status=2
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="checkCancel",method=RequestMethod.POST)
	@ResponseBody
	public Response checkCancel(Integer id,HttpServletRequest request){
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= outStoreService.checkCancel(2,customer.getId(),id,customer.getTypes());
		response.setCode(Integer.parseInt(map.get("resultCode").toString()));
		response.setMessage(map.get("resultInfo").toString());
		return response;
	}
	
	/**
	 * 保存备注
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="saveRemark",method=RequestMethod.POST)
	@ResponseBody
	public Response saveRemark(int id,String remarkContent,HttpServletRequest request) throws Exception{
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			map= outStoreService.saveRemark(id,remarkContent,customer.getId(),customer.getTypes());
			response.setCode(Integer.parseInt(map.get("resultCode").toString()));
			response.setMessage(map.get("resultInfo").toString());
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
	
	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		if(keys !=null && keys.trim().equals("")){
			keys = null;
		}
		Page<OutStore> outStores = outStoreService.findPages(page, status, keys);
		model.addAttribute("outStores", outStores);
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value="distribute",method=RequestMethod.POST)
	@ResponseBody
	public Response distribute(String ids,String customerId,String customerName,HttpServletRequest request) throws NumberFormatException, Exception{
		Response response=new Response();
		Customer customer=sessionService.getLoginInfo(request);
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			map= outStoreService.saveProcessUser(ids,Integer.parseInt(customerId),customerName,customer.getId(),customer.getTypes());
			response.setCode(Integer.parseInt(map.get("resultCode").toString()));
			response.setMessage(map.get("resultInfo").toString());
		}catch(Exception ex){
			ex.printStackTrace();
			response.setCode(Response.ERROR_CODE);
			response.setMessage(ex.getMessage());
		}finally{
			return response;
		}
	}
}
