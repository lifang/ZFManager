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

	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.GET)
	public String saveOrUpdate(HttpServletRequest request, Integer id,
			Integer cityId, String receiver, String address, String moblephone,
			String zipCode, Integer isDefault, Byte status, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		customerAddressService.saveOrUpdateCustomerAddress(id, cityId,
				receiver, address, moblephone, zipCode, isDefault,
				customer.getId(), status);
		List<CustomerAddress> selectCustomerAddress = customerAddressService
				.selectCustomerAddress(customer.getId());
		List<City> cities = cityService.cities(0);
		model.addAttribute("customerAddresses", selectCustomerAddress);
		model.addAttribute("cities", cities);
		return "order/user/customerAddress";
	}
}
