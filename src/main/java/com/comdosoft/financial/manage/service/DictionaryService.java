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
    private DictionaryOpenPrivateInfoMapper dictionaryOpenPrivateInfo;
    @Autowired
    private DictionaryCreditTypeMapper dictionaryCreditTypeMapper;

    public List<DictionaryEncryptCardWay> listAllDictionaryEncryptCardWays() {
        return dictionaryEncryptCardWayMapper.selectAll();
    }

    public List<DictionaryCardType> listAllDictionaryCardTypes() {
        return dictionaryCardTypeMapper.selectAll();
    }

    public List<DictionarySignOrderWay> listAllDictionarySignOrderWays() {
        return dictionarySignOrderWayMapper.selectAll();
    }

    public List<DictionaryTradeStandardRate> listAllDictionaryTradeStandardRates() {
        return dictionaryTradeStandardRateMapper.selectAll();
    }

    public List<DictionaryBillingCycle> listAllDictionaryBillingCycles() {
        return dictionaryBillingCycleMapper.selectAll();
    }

    public List<DictionaryTradeType> listAllDictionaryTradeTypes() {
        return dictionaryTradeTypeMapper.selectAll();
    }

    public List<DictionaryOpenPrivateInfo> listAllDictionaryOpenPrivateInfos() {
        return dictionaryOpenPrivateInfo.selectAll();
    }

    public List<DictionaryCreditType> listAllDictionaryCreditTypes() {
        return dictionaryCreditTypeMapper.selectAll();
    }

    public  DictionaryCreditType findDictionaryCreditType(Integer id){
        return dictionaryCreditTypeMapper.selectByPrimaryKey(id);
    }
    @Transactional(value = "transactionManager")
    public  DictionaryCreditType editDictionaryCreditType(Integer id, String name){
        DictionaryCreditType dictionaryCreditType = dictionaryCreditTypeMapper.selectByPrimaryKey(id);
        dictionaryCreditType.setName(name);
        dictionaryCreditType.setUpdatedAt(new Date());
        dictionaryCreditTypeMapper.updateByPrimaryKey(dictionaryCreditType);
        return dictionaryCreditType;
    }

    @Transactional(value = "transactionManager")
    public  DictionaryCreditType createDictionaryCreditType(String name){
        DictionaryCreditType dictionaryCreditType = new DictionaryCreditType();
        dictionaryCreditType.setName(name);
        dictionaryCreditType.setCreatedAt(new Date());
        dictionaryCreditType.setUpdatedAt(new Date());
        dictionaryCreditTypeMapper.insert(dictionaryCreditType);
        return dictionaryCreditType;
    }

    @Transactional(value = "transactionManager")
    public  void deleteDictionaryCreditType(Integer id){
        dictionaryCreditTypeMapper.deleteByPrimaryKey(id);
    }

}
