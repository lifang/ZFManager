package com.comdosoft.financial.manage.controller.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.OutStore;
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
