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
        return dictionaryOpenPrivateInfoMapper.selectAll();
    }

    public  DictionaryOpenPrivateInfo findDictionaryOpenPrivateInfo(Integer id){
        return dictionaryOpenPrivateInfoMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager")
    public  DictionaryOpenPrivateInfo editDictionaryOpenPrivateInfo(Integer id, Byte infoType, String name, String introduction, String queryMark){
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
    public  DictionaryOpenPrivateInfo createDictionaryOpenPrivateInfo(Byte infoType, String name, String introduction, String queryMark){
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
    public  void deleteDictionaryOpenPrivateInfo(Integer id){
        dictionaryOpenPrivateInfoMapper.deleteByPrimaryKey(id);
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
