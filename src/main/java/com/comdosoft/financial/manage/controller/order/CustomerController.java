package com.comdosoft.financial.manage.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.CustomerService;

@Controller
@RequestMapping("/order/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

    @RequestMapping(value="search",method = RequestMethod.GET)
    public String search(HttpServletRequest request,String customerName,Model model){
    	List<Customer> searchCustomer = customerService.searchCustomer(customerName);
		model.addAttribute("customers", searchCustomer);
        return "order/user/customerSearch";
    }
}
