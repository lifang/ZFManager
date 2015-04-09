package com.comdosoft.financial.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAddressMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerAddressService {
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	
    public List<CustomerAddress> selectCustomerAddress(Integer customerId){
    	return customerAddressMapper.selectCustomerAddress(customerId);
    }

    @Transactional("transactionManager")
    public int saveOrUpdateCustomerAddress(Integer id,Integer cityId,String receiver,
    		String address,String moblephone,String zipCode,
    		Integer isDefault,Integer customerId,Byte status){
    	int result=0;
    	CustomerAddress record=new CustomerAddress();
    	record.setId(id);
    	if(null!=cityId) record.setCityId(cityId);
    	if(null!=receiver) record.setReceiver(receiver);
    	if(null!=address) record.setAddress(address);
    	if(null!=moblephone) record.setMoblephone(moblephone);
    	if(null!=zipCode) record.setZipCode(zipCode);
    	if(null!=isDefault) record.setIsDefault(isDefault);
    	if(null!=customerId) record.setCustomerId(customerId);
    	if(null!=status && (1==status|| 2==status)) record.setStatus(status);
    	if(null==id){
    		record.setIsDefault(2);
    		record.setStatus((byte) 1);
    		record.setCreatedAt(new Date());
    		result=customerAddressMapper.insert(record);
    	}else{
    		record.setUpdatedAt(new Date());
    		result=customerAddressMapper.updateByPrimaryKey(record);
    	}
		return result;
    }
    
    public CustomerAddress get(Integer id){
    	return customerAddressMapper.selectByPrimaryKey(id);
    }
    
}
