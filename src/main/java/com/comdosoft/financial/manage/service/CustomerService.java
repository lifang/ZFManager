package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralRecord;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerRoleRelation;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAgentRelationMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerIntegralRecordMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerRoleRelationMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import com.google.common.base.Strings;

@Service
public class CustomerService {

    @Value("${page.size}")
    private Integer pageSize;
	
	@Autowired
	private CustomerMapper customerMapper;
    @Autowired
    private CustomerRoleRelationMapper customerRoleRelationMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private CustomerIntegralRecordMapper customerIntegralRecordMapper;
    @Autowired
    private CustomerAgentRelationMapper customerAgentRelationMapper;
	/**
	 * 登陆查询 超级管理员 运营 第三方机构
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
	public boolean createCustomer(String name, String email, String password,
                               String phone, Integer city){
        Customer customer = customerMapper.selectByUsername(phone);
		if(customer != null){
            return false;
        }
        customer = new Customer();
		customer.setTypes(Customer.TYPE_CUSTOMER);
        customer.setAccountType(Customer.ACCOUNT_TYPE_PHONE);
        customer.setName(name);
        customer.setEmail(email);
		customer.setPhone(phone);
        customer.setUsername(phone);
        customer.setAccountType(Customer.ACCOUNT_TYPE_PHONE);
        customer.setCityId(city);
		customer.setPassword(DigestUtils.md5Hex(password));
		customer.setIntegral(0);
        customer.setStatus(Customer.STATUS_NORMAL);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
		customer.setUsername(phone);
		customerMapper.insert(customer);
        return true;
	}
	
	@Transactional("transactionManager")
	public Customer saveAndReturn(String passport, String password,
                               String phone, Integer city){
		Customer customer = new Customer();
		customer.setTypes(Customer.TYPE_CUSTOMER);
		if(phone.contains("@")){
			customer.setEmail(phone);
		}else{
			customer.setPhone(phone);
		}
		customer.setCityId(city);
		customer.setPassword(DigestUtils.md5Hex(password));
		customer.setIntegral(0);
        customer.setCreatedAt(new Date());
        customer.setStatus(Customer.STATUS_NORMAL);
        customer.setUpdatedAt(new Date());
        customer.setName(passport);
        customer.setUsername(phone);
		customerMapper.insert(customer);
		return customer;
	}

    @Transactional("transactionManager")
    public boolean createOperate(String account,String name,
    		String password, Integer[] roles){
        Customer customer = customerMapper.selectByUsername(account);
        if(customer != null){
            return false;
        }
        customer = new Customer();
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
        return true;
    }
    
    @Transactional("transactionManager")
    public boolean modifyOperate(Integer id,String account,
    		String name,String password, Integer[] roles){
        Customer customer = customer(id);

        if (!customer.getUsername().equals(account)){
            Customer customer1 = customerMapper.selectByUsername(account);
            if (customer1 == null){
                customer.setUsername(account);
            }else{
                return false;
            }
        }
    	customer.setName(name);
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
        return true;
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
	 * 更新
	 */
	@Transactional("transactionManager")
	public boolean update(Integer id,String name, String email, String password,
			String phone,Integer city){
		Customer customer = customer(id);
		customer.setPhone(phone);
        customer.setEmail(email);
		customer.setCityId(city);
		if(!Strings.isNullOrEmpty(password)){
			customer.setPassword(DigestUtils.md5Hex(password));
        }
        customer.setName(name);
        customer.setUpdatedAt(new Date());
		customerMapper.updateByPrimaryKey(customer);
        return true;
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
    public Customer selectById(Integer id){
        return customerMapper.selectByPrimaryKey(id);
    }

    public List<Customer> findUserAndAgent(String username) {
        return customerMapper.selectUserAndAgent(username + "%");
    }
    
    public List<Customer> searchCustomer(String customerName){
    	if(null!=customerName){
    		customerName="%"+customerName.trim()+"%";
    	}
    	return customerMapper.searchCustomer(customerName);
    }
    
    public List<Customer> searchCustomer(String customerName,Integer agentId){
    	if(null==agentId){
    		return searchCustomer(customerName);
    	}
    	if(null!=customerName){
    		customerName="%"+customerName.trim()+"%";
    	}
    	List<CustomerAgentRelation> selectByAgentId = customerAgentRelationMapper.selectByAgentId(agentId, 1, 1);
    	List<Integer> customerIds=new ArrayList<Integer>();
    	for(CustomerAgentRelation customerAgentRelation:selectByAgentId){
    		customerIds.add(customerAgentRelation.getCustomerId());
    	}
    	if(CollectionUtils.isEmpty(customerIds)){
    		return null;
    	}
    	List<Customer> customerList = customerMapper.searchCustomerWithIds(customerName,customerIds);
		return customerList;
    }

    @Transactional("transactionManager")
    public boolean modifyPwdAndAddress(Integer id, String oldPwd, String newPwd,
                                    Integer cityId, String address, String cellphone){
        Customer c = customerMapper.selectByPrimaryKey(id);
        if(oldPwd != null && newPwd != null
                && !oldPwd.equals("") && !newPwd.equals("")){
            String md5Password = DigestUtils.md5Hex(oldPwd);
            if(!md5Password.equals(c.getPassword())){
                return false;
            }
            md5Password = DigestUtils.md5Hex(newPwd);
            c.setPassword(md5Password);
            customerMapper.updateByPrimaryKey(c);
        }

        List<CustomerAddress> customerAddresses = customerAddressMapper.selectCustomerAddress(c.getId());
        CustomerAddress customerAddress = null;
        if(customerAddresses ==null || customerAddresses.size() == 0){
            customerAddress = new CustomerAddress();
            customerAddress.setCityId(cityId);
            customerAddress.setAddress(address);
            customerAddress.setMoblephone(cellphone);
            customerAddress.setCustomerId(c.getId());
            customerAddress.setIsDefault(CustomerAddress.DEFAULT_TRUE);
            customerAddress.setStatus(CustomerAddress.STATUS_NORMAL);
            customerAddress.setCreatedAt(new Date());
            customerAddress.setUpdatedAt(new Date());
            customerAddressMapper.insert(customerAddress);
        } else {
            customerAddress = customerAddresses.get(0);
            customerAddress.setCityId(cityId);
            customerAddress.setAddress(address);
            customerAddress.setMoblephone(cellphone);
            customerAddress.setUpdatedAt(new Date());
        }

        customerAddressMapper.updateByPrimaryKey(customerAddress);

        return true;

    }

    @Transactional("transactionManager")
    public Customer modifyIntegral(Integer id,String reason, Byte type, Integer num){
        Customer customer = customerMapper.selectByPrimaryKey(id);
        int integral = 0;
        int changeIntegral = 0;
        if(customer.getIntegral() != null){
            integral = customer.getIntegral();
        }
        if(type == CustomerIntegralRecord.TYPE_ADD){
            changeIntegral = num;
        } else if(type == CustomerIntegralRecord.TYPE_SUBTRACT
                && integral >= num){
            changeIntegral -=num;
        }
        if(changeIntegral != 0){
            integral+=changeIntegral;
            customer.setIntegral(integral);
            CustomerIntegralRecord record = new CustomerIntegralRecord();
            record.setCustomerId(id);
            record.setDescription(reason);
            record.setQuantity(changeIntegral);
            record.setTargetType(CustomerIntegralRecord.TARGET_TYPE_ADJUST);
            record.setTypes(type);
            record.setCreatedAt(new Date());
            customerIntegralRecordMapper.insert(record);
            customerMapper.updateByPrimaryKey(customer);
        }
        return customer;

    }

    /**
     * 查询是否存在该账号，排除这个id号
     * @param username
     * @param id
     * @return
     */
	public Boolean findCustomerByUserName(String username, Integer id) {
		List<Customer> list= customerMapper.findCustomerByUserName(username,id);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
