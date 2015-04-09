/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2015年3月19日 上午11:24:07
 */
package com.comdosoft.financial.manage.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerAddressService;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.SessionService;

@Controller
@RequestMapping("/order/customer/address")
public class CustomerAddressController {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.GET)
	public String saveOrUpdate(HttpServletRequest request, Model model, Integer id,
			Integer cityId, String receiver, String address, String moblephone,
			String zipCode, Integer isDefault, Byte status,Integer customerId) {
		Customer customer = customerService.customer(customerId);
		Integer id2 = null;
		if(null!=customer){
			id2 = customer.getId();
		}
		customerAddressService.saveOrUpdateCustomerAddress(id, cityId,
				receiver, address, moblephone, zipCode, isDefault,
				id2, status);
		List<CustomerAddress> selectCustomerAddress = customerAddressService
				.selectCustomerAddress(id2);
		List<City> cities = cityService.cities(0);
		model.addAttribute("customerAddresses", selectCustomerAddress);
		model.addAttribute("cities", cities);
		return "order/customerAddress";
	}
	
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public String query(HttpServletRequest request, Model model, Integer customerId) {
		List<CustomerAddress> selectCustomerAddress = customerAddressService
				.selectCustomerAddress(customerId);
		List<City> cities = cityService.cities(0);
		model.addAttribute("customerAddresses", selectCustomerAddress);
		model.addAttribute("cities", cities);
		Customer customer=customerService.customer(customerId);
		model.addAttribute("customer", customer);
		return "order/customerAddress";
	}
}
