package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.mapper.zhangfu.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
