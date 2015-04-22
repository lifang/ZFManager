package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.mapper.zhangfu.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by junxi.qu on 2015/3/3.
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryEncryptCardWayMapper dictionaryEncryptCardWayMapper;
    @Autowired
    private DictionaryCardTypeMapper dictionaryCardTypeMapper;
    @Autowired
    private DictionarySignOrderWayMapper dictionarySignOrderWayMapper;
    @Autowired
    private DictionaryTradeStandardRateMapper dictionaryTradeStandardRateMapper;
    @Autowired
    private DictionaryBillingCycleMapper dictionaryBillingCycleMapper;
    @Autowired
    private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
    @Autowired
    private DictionaryOpenPrivateInfoMapper dictionaryOpenPrivateInfoMapper;
    @Autowired
    private DictionaryCreditTypeMapper dictionaryCreditTypeMapper;
    @Autowired
    private DictionaryCustomerOrderTypeMapper dictionaryCustomerOrderTypeMapper;
    @Autowired
    private DictionaryCompanyAddressMapper dictionaryCompanyAddressMapper;

    public List<DictionaryEncryptCardWay> listAllDictionaryEncryptCardWays() {
        return dictionaryEncryptCardWayMapper.selectAll();
    }


    public DictionaryEncryptCardWay findDictionaryEncryptCardWay(Integer id) {
        return dictionaryEncryptCardWayMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryEncryptCardWay editDictionaryEncryptCardWay(Integer id, String name) {
        DictionaryEncryptCardWay dictionaryEncryptCardWay = dictionaryEncryptCardWayMapper.selectByPrimaryKey(id);
        dictionaryEncryptCardWay.setEncryptCardWay(name);
        dictionaryEncryptCardWayMapper.updateByPrimaryKey(dictionaryEncryptCardWay);
        return dictionaryEncryptCardWay;
    }

    @Transactional(value = "transactionManager")
    public DictionaryEncryptCardWay createDictionaryEncryptCardWay(String name) {
        DictionaryEncryptCardWay dictionaryEncryptCardWay = new DictionaryEncryptCardWay();
        dictionaryEncryptCardWay.setEncryptCardWay(name);
        dictionaryEncryptCardWay.setCreatedAt(new Date());
        dictionaryEncryptCardWayMapper.insert(dictionaryEncryptCardWay);
        return dictionaryEncryptCardWay;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryEncryptCardWay(Integer id) {
        dictionaryEncryptCardWayMapper.deleteByPrimaryKey(id);
    }

    public List<DictionaryCardType> listAllDictionaryCardTypes() {
        return dictionaryCardTypeMapper.selectAll();
    }


    public DictionaryCardType findDictionaryCardType(Integer id) {
        return dictionaryCardTypeMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryCardType editDictionaryCardType(Integer id, String name) {
        DictionaryCardType dictionaryCardType = dictionaryCardTypeMapper.selectByPrimaryKey(id);
        dictionaryCardType.setCardType(name);
        dictionaryCardTypeMapper.updateByPrimaryKey(dictionaryCardType);
        return dictionaryCardType;
    }

    @Transactional(value = "transactionManager")
    public DictionaryCardType createDictionaryCardType(String name) {
        DictionaryCardType dictionaryCardType = new DictionaryCardType();
        dictionaryCardType.setCardType(name);
        dictionaryCardType.setCreatedAt(new Date());
        dictionaryCardTypeMapper.insert(dictionaryCardType);
        return dictionaryCardType;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryCardType(Integer id) {
        dictionaryCardTypeMapper.deleteByPrimaryKey(id);
    }

    public List<DictionarySignOrderWay> listAllDictionarySignOrderWays() {
        return dictionarySignOrderWayMapper.selectAll();
    }

    public DictionarySignOrderWay findDictionarySignOrderWay(Integer id) {
        return dictionarySignOrderWayMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionarySignOrderWay editDictionarySignOrderWay(Integer id, String name) {
        DictionarySignOrderWay dictionarySignOrderWay = dictionarySignOrderWayMapper.selectByPrimaryKey(id);
        dictionarySignOrderWay.setSignOrderWay(name);
        dictionarySignOrderWayMapper.updateByPrimaryKey(dictionarySignOrderWay);
        return dictionarySignOrderWay;
    }

    @Transactional(value = "transactionManager")
    public DictionarySignOrderWay createDictionarySignOrderWay(String name) {
        DictionarySignOrderWay dictionarySignOrderWay = new DictionarySignOrderWay();
        dictionarySignOrderWay.setSignOrderWay(name);
        dictionarySignOrderWay.setCreatedAt(new Date());
        dictionarySignOrderWayMapper.insert(dictionarySignOrderWay);
        return dictionarySignOrderWay;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionarySignOrderWay(Integer id) {
        dictionarySignOrderWayMapper.deleteByPrimaryKey(id);
    }

    public List<DictionaryTradeStandardRate> listAllDictionaryTradeStandardRates() {
        return dictionaryTradeStandardRateMapper.selectAll();
    }

    public DictionaryTradeStandardRate findDictionaryTradeStandardRate(Integer id) {
        return dictionaryTradeStandardRateMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryTradeStandardRate editDictionaryTradeStandardRate(Integer id, String name, String rate, String description) {
        DictionaryTradeStandardRate dictionaryTradeStandardRate = dictionaryTradeStandardRateMapper.selectByPrimaryKey(id);
        dictionaryTradeStandardRate.setMerchantTypeName(name);
        dictionaryTradeStandardRate.setBaseRate(rate);
        dictionaryTradeStandardRate.setDescription(description);
        dictionaryTradeStandardRate.setUpdatedAt(new Date());
        dictionaryTradeStandardRateMapper.updateByPrimaryKey(dictionaryTradeStandardRate);
        return dictionaryTradeStandardRate;
    }

    @Transactional(value = "transactionManager")
    public DictionaryTradeStandardRate createDictionaryTradeStandardRate(String name, String rate, String description) {
        DictionaryTradeStandardRate dictionaryTradeStandardRate = new DictionaryTradeStandardRate();
        dictionaryTradeStandardRate.setMerchantTypeName(name);
        dictionaryTradeStandardRate.setBaseRate(rate);
        dictionaryTradeStandardRate.setDescription(description);
        dictionaryTradeStandardRate.setCreatedAt(new Date());
        dictionaryTradeStandardRate.setUpdatedAt(new Date());
        dictionaryTradeStandardRateMapper.insert(dictionaryTradeStandardRate);
        return dictionaryTradeStandardRate;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryTradeStandardRate(Integer id) {
        dictionaryTradeStandardRateMapper.deleteByPrimaryKey(id);
    }


    public List<DictionaryBillingCycle> listAllDictionaryBillingCycles() {
        return dictionaryBillingCycleMapper.selectAll();
    }

    public List<DictionaryTradeType> listAllDictionaryTradeTypes() {
        return dictionaryTradeTypeMapper.selectAll();
    }


    public DictionaryTradeType findDictionaryTradeType(Integer id) {
        return dictionaryTradeTypeMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryTradeType editDictionaryTradeType(Integer id, Byte tradeType, String tradeValue) {
        DictionaryTradeType dictionaryTradeType = dictionaryTradeTypeMapper.selectByPrimaryKey(id);
        dictionaryTradeType.setTradeType(tradeType);
        dictionaryTradeType.setTradeValue(tradeValue);
        dictionaryTradeType.setUpdatedAt(new Date());
        dictionaryTradeTypeMapper.updateByPrimaryKey(dictionaryTradeType);
        return dictionaryTradeType;
    }

    @Transactional(value = "transactionManager")
    public DictionaryTradeType createDictionaryTradeType(Byte tradeType, String tradeValue) {
        DictionaryTradeType dictionaryTradeType = new DictionaryTradeType();
        dictionaryTradeType.setTradeType(tradeType);
        dictionaryTradeType.setTradeValue(tradeValue);
        dictionaryTradeType.setCreatedAt(new Date());
        dictionaryTradeType.setUpdatedAt(new Date());
        dictionaryTradeTypeMapper.insert(dictionaryTradeType);
        return dictionaryTradeType;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryTradeType(Integer id) {
        dictionaryTradeTypeMapper.deleteByPrimaryKey(id);
    }


    public List<DictionaryOpenPrivateInfo> listAllDictionaryOpenPrivateInfos() {
        return dictionaryOpenPrivateInfoMapper.selectAll();
    }

    public DictionaryOpenPrivateInfo findDictionaryOpenPrivateInfo(Integer id) {
        return dictionaryOpenPrivateInfoMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryOpenPrivateInfo editDictionaryOpenPrivateInfo(Integer id, Byte infoType, String name, String introduction, String queryMark) {
        DictionaryOpenPrivateInfo dictionaryOpenPrivateInfo = dictionaryOpenPrivateInfoMapper.selectByPrimaryKey(id);
        dictionaryOpenPrivateInfo.setInfoType(infoType);
        dictionaryOpenPrivateInfo.setName(name);
        dictionaryOpenPrivateInfo.setIntroduction(introduction);
        dictionaryOpenPrivateInfo.setQueryMark(queryMark);
        dictionaryOpenPrivateInfo.setUpdatedAt(new Date());
        dictionaryOpenPrivateInfoMapper.updateByPrimaryKey(dictionaryOpenPrivateInfo);
        return dictionaryOpenPrivateInfo;
    }

    @Transactional(value = "transactionManager")
    public DictionaryOpenPrivateInfo createDictionaryOpenPrivateInfo(Byte infoType, String name, String introduction, String queryMark) {
        DictionaryOpenPrivateInfo dictionaryOpenPrivateInfo = new DictionaryOpenPrivateInfo();
        dictionaryOpenPrivateInfo.setInfoType(infoType);
        dictionaryOpenPrivateInfo.setName(name);
        dictionaryOpenPrivateInfo.setIntroduction(introduction);
        dictionaryOpenPrivateInfo.setQueryMark(queryMark);
        dictionaryOpenPrivateInfo.setCreatedAt(new Date());
        dictionaryOpenPrivateInfo.setUpdatedAt(new Date());
        dictionaryOpenPrivateInfoMapper.insert(dictionaryOpenPrivateInfo);
        return dictionaryOpenPrivateInfo;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryOpenPrivateInfo(Integer id) {
        dictionaryOpenPrivateInfoMapper.deleteByPrimaryKey(id);
    }

    public List<DictionaryCreditType> listAllDictionaryCreditTypes() {
        return dictionaryCreditTypeMapper.selectAll();
    }

    public DictionaryCreditType findDictionaryCreditType(Integer id) {
        return dictionaryCreditTypeMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryCreditType editDictionaryCreditType(Integer id, String name) {
        DictionaryCreditType dictionaryCreditType = dictionaryCreditTypeMapper.selectByPrimaryKey(id);
        dictionaryCreditType.setName(name);
        dictionaryCreditType.setUpdatedAt(new Date());
        dictionaryCreditTypeMapper.updateByPrimaryKey(dictionaryCreditType);
        return dictionaryCreditType;
    }

    @Transactional(value = "transactionManager")
    public DictionaryCreditType createDictionaryCreditType(String name) {
        DictionaryCreditType dictionaryCreditType = new DictionaryCreditType();
        dictionaryCreditType.setName(name);
        dictionaryCreditType.setCreatedAt(new Date());
        dictionaryCreditType.setUpdatedAt(new Date());
        dictionaryCreditTypeMapper.insert(dictionaryCreditType);
        return dictionaryCreditType;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryCreditType(Integer id) {
        dictionaryCreditTypeMapper.deleteByPrimaryKey(id);
    }

    public List<DictionaryCustomerOrderType> listAllDictionaryCustomerOrderTypes() {
        return dictionaryCustomerOrderTypeMapper.selectAll();
    }

    public DictionaryCustomerOrderType findDictionaryCustomerOrderType(Integer id) {
        return dictionaryCustomerOrderTypeMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryCustomerOrderType editDictionaryCustomerOrderType(Integer id, String name) {
        DictionaryCustomerOrderType dictionaryCustomerOrderType = dictionaryCustomerOrderTypeMapper.selectByPrimaryKey(id);
        dictionaryCustomerOrderType.setName(name);
        dictionaryCustomerOrderTypeMapper.updateByPrimaryKey(dictionaryCustomerOrderType);
        return dictionaryCustomerOrderType;
    }

    @Transactional(value = "transactionManager")
    public DictionaryCustomerOrderType createDictionaryCustomerOrderType(String name) {
        DictionaryCustomerOrderType dictionaryCustomerOrderType = new DictionaryCustomerOrderType();
        dictionaryCustomerOrderType.setName(name);
        dictionaryCustomerOrderType.setCreatedAt(new Date());
        dictionaryCustomerOrderTypeMapper.insert(dictionaryCustomerOrderType);
        return dictionaryCustomerOrderType;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryCustomerOrderType(Integer id) {
        dictionaryCustomerOrderTypeMapper.deleteByPrimaryKey(id);
    }

    public List<DictionaryCompanyAddress> listAllDictionaryCompanyAddresss() {
        return dictionaryCompanyAddressMapper.selectAll();
    }

    public DictionaryCompanyAddress findDictionaryCompanyAddress(Integer id) {
        return dictionaryCompanyAddressMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public DictionaryCompanyAddress editDictionaryCompanyAddress(Integer id, String name) {
        DictionaryCompanyAddress dictionaryCompanyAddress = dictionaryCompanyAddressMapper.selectByPrimaryKey(id);
        dictionaryCompanyAddress.setCompanyAddress(name);
        dictionaryCompanyAddressMapper.updateByPrimaryKey(dictionaryCompanyAddress);
        return dictionaryCompanyAddress;
    }

    @Transactional(value = "transactionManager")
    public DictionaryCompanyAddress createDictionaryCompanyAddress(String address) {
        DictionaryCompanyAddress dictionaryCompanyAddress = new DictionaryCompanyAddress();
        dictionaryCompanyAddress.setCompanyAddress(address);
        dictionaryCompanyAddress.setCreatedAt(new Date());
        dictionaryCompanyAddressMapper.insert(dictionaryCompanyAddress);
        return dictionaryCompanyAddress;
    }

    @Transactional(value = "transactionManager")
    public void deleteDictionaryCompanyAddress(Integer id) {
        dictionaryCompanyAddressMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
	public DictionaryBillingCycle findDictionaryBillingCycle(Integer id) {
		return dictionaryBillingCycleMapper.selectByPrimaryKey(id);
	}

    @Transactional(value = "transactionManager")
	public DictionaryBillingCycle editDictionaryBillingCycle(Integer id,
			String name ,String rate,String description) {
		DictionaryBillingCycle billingCycle = dictionaryBillingCycleMapper.selectByPrimaryKey(id);
		billingCycle.setName(name);
		billingCycle.setServiceRate(Integer.valueOf(rate));
		billingCycle.setDescription(description);
		billingCycle.setUpdatedAt(new Date());
		dictionaryBillingCycleMapper.updateByPrimaryKey(billingCycle);
		return billingCycle;
	}

    @Transactional(value = "transactionManager")
	public DictionaryBillingCycle createDictionaryBillingCycle(String name,String rate,String description) {
    	DictionaryBillingCycle billingCycle = new DictionaryBillingCycle();
    	billingCycle.setName(name);
		billingCycle.setServiceRate(Integer.valueOf(rate));
		billingCycle.setDescription(description);
		billingCycle.setCreatedAt(new Date());
		dictionaryBillingCycleMapper.insert(billingCycle);
		return billingCycle;
	}

    @Transactional(value = "transactionManager")
	public void deleteDictionaryBillingCycle(Integer id) {
		dictionaryBillingCycleMapper.deleteByPrimaryKey(id);
		
	}

	public List<DictionaryBillingCycle> listAllDictionaryBillingCycle() {
		return dictionaryBillingCycleMapper.selectAll();
	}
}
