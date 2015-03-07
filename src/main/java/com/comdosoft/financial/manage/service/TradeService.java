package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.mapper.trades.TradeRecordMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.DictionaryTradeTypeMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gookin on 15/3/7.
 */
@Service
public class TradeService {

    @Value("${page.size}")
    private Integer pageSize;
    @Autowired
    private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
    @Autowired
    private TradeRecordMapper tradeRecordMapper;

    public List<DictionaryTradeType> allTradeTypes(){
        return dictionaryTradeTypeMapper.selectAll();
    }

    public DictionaryTradeType tradeType(Integer id){
        return dictionaryTradeTypeMapper.selectByPrimaryKey(id);
    }

    public Page<TradeRecord> tradeRecordPage(Integer page,Integer type,Integer status,Date start,Date end){
        PageRequest request = new PageRequest(page,pageSize);
        long count = tradeRecordMapper.countPage(start,end,type,status);
        List<TradeRecord> records = tradeRecordMapper.selectPage(request,start,end,type,status);
        return new Page<>(request,records,count);
    }

    public Map<String,Long> pageProfit(Integer page,Integer type,Integer status,Date start,Date end){
        return tradeRecordMapper.selectPageProfit(start,end,type,status);
    }
}
