package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerRoleRelation;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerRoleRelationMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService {

    @Value("${page.size}")
    private Integer pageSize;
	
	@Autowired
	private CustomerMapper customerMapper;
    @Autowired
    private CustomerRoleRelationMapper customerRoleRelationMapper;

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
	public void createCustomer(String passport, String password,
                               String phone, Integer city){
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
	
	@Transactional("transactionManager")
	public Customer saveAndReturn(String passport, String password,
                               String phone, Integer city){
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
			customer.setName(phone);
		}else{
			customer.setUsername(passport);
			customer.setName(passport);
		}
		customerMapper.insert(customer);
		return customer;
	}

    @Transactional("transactionManager")
    public void createOperate(String account,String name,
    		String password, Integer[] roles){
        Customer customer = new Customer();
        customer.setTypes(Customer.TYPE_OPERATE);
        customer.setName(name);
        customer.setPassword(DigestUtils.md5Hex(password));
        customer.setUsername(account);
        customer.setIntegral(0);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        customer.setStatus(Customer.STATUS_NORMAL);
        customerMapper.insert(customer);
        for(Integer role : roles){
            CustomerRoleRelation crr = new CustomerRoleRelation();
            crr.setUpdatedAt(new Date());
            crr.setCreatedAt(new Date());
            crr.setCustomerId(customer.getId());
            crr.setRoleId(role);
            customerRoleRelationMapper.insert(crr);
        }
    }
    
    @Transactional("transactionManager")
    public void modifyOperate(Integer id,String account,
    		String name,String password, Integer[] roles){
    	Customer customer = customer(id);
    	customer.setName(name);
    	customer.setUsername(account);
    	customer.setUpdatedAt(new Date());
    	if(StringUtils.hasLength(password)){
    		customer.setPassword(DigestUtils.md5Hex(password));
    	}
    	customerMapper.updateByPrimaryKey(customer);
    	customerRoleRelationMapper.deleteByCustomerId(id);
        for(Integer role : roles){
            CustomerRoleRelation crr = new CustomerRoleRelation();
            crr.setUpdatedAt(new Date());
            crr.setCreatedAt(new Date());
            crr.setCustomerId(customer.getId());
            crr.setRoleId(role);
            customerRoleRelationMapper.insert(crr);
        }
    }
	
	public Page<Customer> listCustomerPage(Integer page,String query){
		return listPage(page, query, Customer.TYPE_CUSTOMER, null);
	}
	
	public Page<Customer> listOperatePage(Integer page,String query,Byte status){
		return listPage(page, query, Customer.TYPE_OPERATE, status);
	}
	
	/**
	 * 列表
	 * @param page 页数
	 * @param query 查询条件
	 * @return
	 */
	public Page<Customer> listPage(Integer page,String query,Byte type,Byte status){
		PageRequest request = new PageRequest(page, pageSize);
		List<Customer> customers = customerMapper.selectCustomerPageList(
				request,query,type,status);
		long total = customerMapper.countTotalCustomer(query,type,status);
		return new Page<>(request, customers, total);
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

    @Transactional("transactionManager")
    public void modifyPwd(Integer id, String pwd){
        Customer c = customerMapper.selectByPrimaryKey(id);
        String md5Password = DigestUtils.md5Hex(pwd);
        c.setPassword(md5Password);
        customerMapper.updateByPrimaryKey(c);
    }

    public Customer selectByUsername(String username){
        return customerMapper.selectByUsername(username);
    }

    public List<Customer> findUserAndAgent(String username) {
        return customerMapper.selectUserAndAgent(username + "%");
    }
    
    public List<Customer> searchCustomer(String customerName){
    	if(null!=customerName){
    		customerName="%"+customerName+"%";
    	}
    	return customerMapper.searchCustomer(customerName);
    }
}
