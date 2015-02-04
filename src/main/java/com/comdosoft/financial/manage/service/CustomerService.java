package com.comdosoft.financial.manage.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import com.google.common.base.Strings;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;

	/**
	 * 登陆查询
	 * @param passport
	 * @param password
	 * @return
	 */
	public Customer login(String passport,String password) {
		String md5Password = DigestUtils.md5Hex(password);
		return customerMapper.selectByLogin(passport, md5Password);
	}
	
	/**
	 * 创建
	 */
	@Transactional("transactionManager")
	public void create(String passport,String password,
			String phone,Integer city){
		Customer customer = new Customer();
		customer.setTypes(Customer.TYPE_CUSTOMER);
		customer.setPhone(phone);
		customer.setCityId(city);
		customer.setPassword(DigestUtils.md5Hex(password));
		customer.setIntegral(0);
		customer.setCreatedAt(new Date());
		customer.setStatus(Customer.STATUS_NORMAL);
		customer.setUpdatedAt(new Date());
		if(Strings.isNullOrEmpty(passport)){
			customer.setUsername(phone);
		}else{
			customer.setUsername(passport);
		}
		customerMapper.insert(customer);
	}
	
	/**
	 * 列表
	 * @param page
	 * @return
	 */
	public Page<Customer> listPage(Integer page){
		PageRequest request = new PageRequest(page, Constants.PAGE_SIZE);
		List<Customer> customers = customerMapper.selectCustomerPageList(request);
		long total = customerMapper.countTotalCustomer();
		return new Page<Customer>(request, customers, total);
	}
	
	public Customer customer(Integer id) {
		return customerMapper.selectByPrimaryKey(id);
	}
}
