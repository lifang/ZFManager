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
	 * @param page 页数
	 * @param query 查询条件
	 * @return
	 */
	public Page<Customer> listPage(Integer page,String query){
		PageRequest request = new PageRequest(page, Constants.PAGE_SIZE);
		List<Customer> customers = customerMapper.selectCustomerPageList(request,query);
		long total = customerMapper.countTotalCustomer(query);
		return new Page<Customer>(request, customers, total);
	}
	
	public Customer customer(Integer id) {
		return customerMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 创建
	 */
	@Transactional("transactionManager")
	public void update(Integer id,String passport,String password,
			String phone,Integer city){
		Customer customer = customer(id);
		customer.setPhone(phone);
		customer.setCityId(city);
		if(!Strings.isNullOrEmpty(password)){
			customer.setPassword(DigestUtils.md5Hex(password));
		}
		customer.setUpdatedAt(new Date());
		if(Strings.isNullOrEmpty(passport)){
			customer.setUsername(phone);
		}else{
			customer.setUsername(passport);
		}
		customerMapper.updateByPrimaryKey(customer);
	}
	/**
	 * 停用/启用
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public Customer updateStatus(Integer id){
		Customer customer = customer(id);
		if(customer.getStatus()==Customer.STATUS_NORMAL){
			customer.setStatus(Customer.STATUS_STOP);
		}else if(customer.getStatus()==Customer.STATUS_STOP){
			customer.setStatus(Customer.STATUS_NORMAL);
		}
		customerMapper.updateByPrimaryKey(customer);
		return customer;
	}
	
	@Transactional("transactionManager")
	public boolean modifyPwd(Customer customer,String oldPwd,String pwd){
		Customer c = customerMapper.selectByPrimaryKey(customer.getId());
		String md5OldPassword = DigestUtils.md5Hex(oldPwd);
		if(!c.getPassword().equals(md5OldPassword)){
			return false;
		}
		String md5Password = DigestUtils.md5Hex(pwd);
		c.setPassword(md5Password);
		customerMapper.updateByPrimaryKey(c);
		return true;
	}
}
