package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.mapper.zhangfu.FactoryMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FactoryService {

    @Value("${page.factory.size}")
    private Integer pageSize;

	@Autowired
	private FactoryMapper factoryMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

	public List<Factory> findCheckedFactories(){
		return factoryMapper.selectFactoriesByTypeAndStatus(Factory.TYPE_PAYMENT, Factory.STATUS_CHECKED);
	}

    public Factory findCustomerFactory(Integer customerId){
        return factoryMapper.findFactoryByCustomerId(customerId);
    }

    public List<Factory> findCheckedPayFactories(){
        return factoryMapper.selectFactoriesByTypeAndStatus(Factory.TYPE_PAYMENT, Factory.STATUS_CHECKED);
    }

    public Page<Factory> findPages(int page, Byte status, String keys){
        if (keys != null) {
            keys = "%"+keys+"%";
        }
        long count = factoryMapper.countByKeys(status, keys);
        if (count == 0) {
            return new Page<Factory>(new PageRequest(1, pageSize), new ArrayList<Factory>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<Factory> result = factoryMapper.findPageFactoryByKeys(request, status, keys);
        Page<Factory> factories = new Page<>(request, result, count);
        if (factories.getCurrentPage() > factories.getTotalPage()) {
            request = new PageRequest(factories.getTotalPage(), pageSize);
            result = factoryMapper.findPageFactoryByKeys(request, status, keys);
            factories = new Page<>(request, result, count);
        }
        return factories;
    }

    /**
     * 初审不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusFirstUnCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK) {
            factory.setStatus(Factory.STATUS_FIRST_UN_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 初审通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusFirstCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK
                || factory.getStatus() == Factory.STATUS_FIRST_UN_CHECKED) {
            factory.setStatus(Factory.STATUS_FIRST_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusUnCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK
                || factory.getStatus() == Factory.STATUS_FIRST_CHECKED) {
            factory.setStatus(Factory.STATUS_UN_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 审核通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK
                || factory.getStatus() == Factory.STATUS_FIRST_UN_CHECKED
                || factory.getStatus() == Factory.STATUS_FIRST_CHECKED
                || factory.getStatus() == Factory.STATUS_UN_CHECKED) {
            factory.setStatus(Factory.STATUS_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 停止
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusStop(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_CHECKED) {
            factory.setStatus(Factory.STATUS_STOP);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusWaitingFirstCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_STOP) {
            factory.setStatus(Factory.STATUS_WAITING_FIRST_CHECK);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    public Factory findFactoryInfo(Integer id){
        return factoryMapper.findFactoryInfo(id);
    }
    @Transactional("transactionManager")
    public boolean create(Byte types, String name, String username, String password, Byte accountType,
                       String logoFilePath, String websiteUrl, String description,
                       Integer cityId, String address, String cellphone){
        Customer customer = customerMapper.selectByUsername(username);
        if (customer != null){
            return false;
        }
        customer = new Customer();
        customer.setUsername(username);
        String md5Password = DigestUtils.md5Hex(password);
        customer.setPassword(md5Password);
        customer.setTypes(Customer.TYPE_THIRD_PARTY);
        customer.setCityId(cityId);
        customer.setIntegral(0);
        customer.setStatus(Customer.STATUS_NORMAL);
        customer.setAccountType(accountType);
        customer.setCreatedAt(new Date());
        customerMapper.insert(customer);

        Factory factory = new Factory();
        factory.setStatus(Factory.STATUS_WAITING_FIRST_CHECK);
        factory.setTypes(types);
        factory.setName(name);
        factory.setLogoFilePath(logoFilePath);
        factory.setWebsiteUrl(websiteUrl);
        factory.setDescription(description);
        factory.setCustomerId(customer.getId());
        factory.setCreatedAt(new Date());
        factory.setUpdatedAt(new Date());
        factoryMapper.insert(factory);

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCityId(cityId);
        customerAddress.setAddress(address);
        customerAddress.setMoblephone(cellphone);
        customerAddress.setCustomerId(factory.getCustomerId());
        customerAddress.setIsDefault(CustomerAddress.DEFAULT_TRUE);
        customerAddress.setStatus(CustomerAddress.STATUS_NORMAL);
        customerAddress.setCreatedAt(new Date());
        customerAddress.setUpdatedAt(new Date());
        customerAddressMapper.insert(customerAddress);
        return true;
    }

    @Transactional("transactionManager")
    public boolean update(Integer id, Byte types, String name, String username, String password, Byte accountType,
                       String logoFilePath, String websiteUrl, String description,
                       Integer cityId, String address, String cellphone){
        Factory factory = factoryMapper.findFactoryInfo(id);
        factory.setTypes(types);
        factory.setName(name);
        factory.setLogoFilePath(logoFilePath);
        factory.setWebsiteUrl(websiteUrl);
        factory.setDescription(description);
        factory.setUpdatedAt(new Date());

        List<CustomerAddress> customerAddresses = customerAddressMapper.selectCustomerAddress(factory.getCustomerId());
        CustomerAddress customerAddress = null;
        if(customerAddresses ==null || customerAddresses.size() == 0){
            customerAddress = new CustomerAddress();
            customerAddress.setCityId(cityId);
            customerAddress.setAddress(address);
            customerAddress.setMoblephone(cellphone);
            customerAddress.setCustomerId(factory.getCustomerId());
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



        Customer customer = factory.getCustomer();
        if (!customer.getUsername().equals(username)){
            Customer customer1 = customerMapper.selectByUsername(username);
            if (customer1 == null){
                customer.setUsername(username);
                customer.setAccountType(accountType);
            }else{
                return false;
            }
        }
        if (password != null && !password.equals("")){
            String md5Password = DigestUtils.md5Hex(password);
            customer.setPassword(md5Password);
        }
        customer.setCityId(cityId);
        customerMapper.updateByPrimaryKey(customer);
        factoryMapper.updateByPrimaryKey(factory);
        customerAddressMapper.updateByPrimaryKey(customerAddress);
        return true;
    }

}
