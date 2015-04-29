package com.comdosoft.financial.manage.controller.factory;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by quqiang on 15/4/9.
 */
@Controller("FactoryUserController")
@RequestMapping("/factory/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerAddressService customerAddressService;
    @Autowired
    private CityService cityService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value="edit",method= RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        Customer customer = sessionService.getLoginInfo(request);
        List<CustomerAddress> addresses = customerAddressService.selectCustomerAddress(customer.getId());
        if(addresses != null && addresses.size() > 0){
            model.addAttribute("address", addresses.get(0));
        }
        List<City> provinces = cityService.provinces();
        model.addAttribute("provinces", provinces);
        return "factory_role/user/edit";
    }

    @RequestMapping(value="edit",method= RequestMethod.POST)
    @ResponseBody
    public Response edit(String oldPassword, String newPassword, Integer cityId,
                         String address, String cellphone, Model model, HttpServletRequest request) {
        Customer customer = sessionService.getLoginInfo(request);
        boolean result = customerService.modifyPwdAndAddress(customer.getId(), oldPassword, newPassword, cityId, address, cellphone);
        if(result){
            return Response.getSuccess(null);
        } else {
            return Response.getError("修改失败！");
        }
    }

}
