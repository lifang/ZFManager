package com.comdosoft.financial.manage.controller.factory;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 15-4-7.
 */
@Controller
@RequestMapping("/factory/pos")
public class PosController {

    @Autowired
    private GoodService goodService ;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/pos/list";
    }

    @RequestMapping(value="page",method=RequestMethod.GET)
    public String page(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/pos/pagePos";
    }

    private void findPage(Integer customerId, Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Good> goods = goodService.findPages(customerId, page, status, keys);
        model.addAttribute("goods", goods);
    }
}
