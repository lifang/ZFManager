package com.comdosoft.financial.manage.controller.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.task.Good;
import com.comdosoft.financial.manage.domain.zhangfu.task.OutStore;
import com.comdosoft.financial.manage.service.task.OutStoreService;
import com.comdosoft.financial.manage.utils.page.Page;


@Controller
@RequestMapping("/task/outStore")
public class OutStoreController {
	private Logger LOG = LoggerFactory.getLogger(OutStoreController.class);
	
	@Autowired
	private OutStoreService outStoreService ;
	
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
	public String info(@PathVariable Integer id, Model model){
		OutStore outStore = outStoreService.findOutStoreInfo(id);
		model.addAttribute("outStore", outStore);
		return "task/outStore/info";
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
		String address=outStoreService.getAddressInit(id);
		model.addAttribute("goods", goods);
		model.addAttribute("address", address);
		return "task/outStore/addOutRecord";
	}
	
	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<OutStore> outStores = outStoreService.findPages(page, status, keys);
		model.addAttribute("outStores", outStores);
	}
	
	
}
