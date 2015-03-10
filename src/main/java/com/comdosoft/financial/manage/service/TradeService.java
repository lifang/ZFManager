package com.comdosoft.financial.manage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.trades.Profit;
import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.mapper.trades.ProfitMapper;
import com.comdosoft.financial.manage.mapper.trades.TradeRecordMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.AgentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.DictionaryTradeTypeMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

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
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private ProfitMapper profitMapper;

    public List<DictionaryTradeType> allTradeTypes(){
        return dictionaryTradeTypeMapper.selectAll();
    }

    public DictionaryTradeType tradeType(Integer id){
        return dictionaryTradeTypeMapper.selectByPrimaryKey(id);
    }
    
    public TradeRecord tradeRecord(Integer id){
    	TradeRecord tradeRecord = tradeRecordMapper.selectByPrimaryKey(id);
    	if(tradeRecord!=null && tradeRecord.getAgentId()!=null){
    		Agent agent = agentMapper.selectByPrimaryKey(tradeRecord.getAgentId());
    		tradeRecord.setAgent(agent);
    	}
    	return tradeRecord;
    }
    
    public Profit tradeRecordProfit(Integer tradeRecordId){
    	return profitMapper.selectByTradeRecordId(tradeRecordId);
    }

    public Page<TradeRecord> tradeRecordPage(Integer page,Integer type,Integer status,Date start,Date end){
        PageRequest request = new PageRequest(page,pageSize);
        long count = tradeRecordMapper.countPage(start,end,type,status);
        List<TradeRecord> records = tradeRecordMapper.selectPage(request,start,end,type,status);
        for(TradeRecord record : records){
        	if(record.getAgentId()!=null){
        		Agent agent = agentMapper.selectByPrimaryKey(record.getAgentId());
        		record.setAgent(agent);
        	}
        }
        return new Page<>(request,records,count);
    }

    public Map<String,Long> pageProfit(Integer type,Integer status,Date start,Date end){
        return tradeRecordMapper.selectPageProfit(start,end,type,status);
    }

    public List<Map<String,Object>> profitStatistics(){
    	List<Map<String,Object>> statistics = profitMapper.selectStatistics();
    	statistics.forEach(map -> {
    		Integer id = (Integer)map.get("agent_id");
    		Agent agent = agentMapper.selectByPrimaryKey(id);
    		map.put("agent", agent);
    	});
        return statistics;
    }
}
