package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.service.CustomerAddressService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.utils.FreeMarkerUtils;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by quqiang on 15/3/24.
 */
@Controller
@RequestMapping("/system/factory")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;
    @Autowired
    private CustomerAddressService customerAddressService;
    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "system/factory_list";
    }

    @RequestMapping(value="page",method=RequestMethod.GET)
    public String page(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "system/factory_list_page";
    }


    @RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
    public String firstUnCheck(@PathVariable Integer id, Model model){
        Factory factory = factoryService.statusFirstUnCheck(id);
        model.addAttribute("factory", factory);
        return "system/factory_list_page_row";
    }

    @RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
    public String firstCheck(@PathVariable Integer id, Model model){
        Factory factory = factoryService.statusFirstCheck(id);
        model.addAttribute("factory", factory);
        return "system/factory_list_page_row";
    }

    @RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
    public String unCheck(@PathVariable Integer id, Model model){
        Factory factory = factoryService.statusUnCheck(id);
        model.addAttribute("factory", factory);
        return "system/factory_list_page_row";
    }

    @RequestMapping(value="{id}/check",method=RequestMethod.GET)
    public String check(@PathVariable Integer id, Model model){
        Factory factory = factoryService.statusCheck(id);
        model.addAttribute("factory", factory);
        return "system/factory_list_page_row";
    }

    @RequestMapping(value="{id}/stop",method=RequestMethod.GET)
    public String stop(@PathVariable Integer id, Model model){
        Factory factory = factoryService.statusStop(id);
        model.addAttribute("factory", factory);
        return "system/factory_list_page_row";
    }

    @RequestMapping(value="{id}/start",method=RequestMethod.GET)
    public String start(@PathVariable Integer id, Model model){
        Factory factory = factoryService.statusWaitingFirstCheck(id);
        model.addAttribute("factory", factory);
        return "system/factory_list_page_row";
    }

    @RequestMapping(value="{id}/resetpwd",method=RequestMethod.GET)
    public String resetPassword(@PathVariable Integer id, Model model){
        Factory factory = factoryService.findFactoryInfo(id);
        model.addAttribute("customer", factory.getCustomer());
        return "system/factory_reset_pwd";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model) throws Exception{
        Factory factory = factoryService.findFactoryInfo(id);
        List<CustomerAddress> addresses = customerAddressService.selectCustomerAddress(factory.getCustomerId());
        model.addAttribute("factory", factory);
        model.addAttribute("addresses", addresses);
        model.addAttribute("Factory", FreeMarkerUtils.useClass(Factory.class.getName()));
        return "system/factory_info";
    }

    private void findPage(Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Factory> factories = factoryService.findPages(page, status, keys);
        model.addAttribute("page", factories);
    }

}
