package com.comdosoft.financial.manage.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger LOG = LoggerFactory.getLogger(TradeService.class);

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
    private SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");

    public List<DictionaryTradeType> allTradeTypes(){
        return dictionaryTradeTypeMapper.selectAll();
    }
    
    public Map<String,Integer> allTradeNameMap(){
    	return allTradeTypes().stream()
    			.collect(Collectors.toMap(DictionaryTradeType::getTradeValue, DictionaryTradeType::getId));
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
    
    public List<Integer> importTrades(InputStream stream) throws InvalidFormatException, IOException{
    	Workbook workbook = WorkbookFactory.create(stream);
    	Sheet sheet = workbook.getSheetAt(0);
    	int lastRow = sheet.getLastRowNum();
    	Map<String,Integer> tradeNameMap = allTradeNameMap();
    	List<Integer> errorRowNum = new ArrayList<>();
    	for(int i=3;i<=lastRow;++i){
    		Row row = sheet.getRow(i);
    		boolean result = false;
    		try {
				result = processTradeRow(row, tradeNameMap);
			} catch (Exception e) {
				LOG.error("",e);
			}
    		if(!result){
    			errorRowNum.add(i+1);
    		}
    	}
    	return errorRowNum;
    }
    
    private boolean processTradeRow(Row row,Map<String,Integer> tradeNameMap){
    	//交易订单号0 商户订单号1 子商户号2 商户名3 终端号4 终端序列号5 卡号6 交易金额7 交易时间8 交易类别9 交易状态10 交易手续费11
    	String orderNum = cellStringValue(row, 0);
    	String merchantOrderNum = cellStringValue(row, 1);
    	String childMercantOrderNum = cellStringValue(row, 2);
    	String merchantName = cellStringValue(row, 3);
    	String terminalNum = cellStringValue(row, 4);
//    	String terminalSerialNum = cellStringValue(row, 5); //不需要插入数据库
    	String cardNum = cellStringValue(row, 6);
    	String amount = cellStringValue(row, 7);
    	String tradeTime = cellStringValue(row, 8);
    	String tradeType = cellStringValue(row, 9);
    	String tradeStatus = cellStringValue(row, 10);
    	String tradeFee = cellStringValue(row, 11);
    	//检查消费类别
    	Integer tradeTypeId = tradeNameMap.get(tradeType);
    	if(tradeTypeId == null){
    		return false;
    	}
    	//检查交易状态
    	Integer tradeStatusInt = null;
    	if("支付成功".equals(tradeStatus)){
    		tradeStatusInt = TradeRecord.TRADE_STATUS_SUCCESS;
    	}else if("支付失败".equals(tradeStatus)){
    		tradeStatusInt = TradeRecord.TRADE_STATUS_FAIL;
    	}
    	if(tradeStatusInt == null){
    		return false;
    	}
    	//检查交易时间
    	Date tradeTimeDate = null;
    	try {
			tradeTimeDate = formater.parse(tradeTime);
		} catch (ParseException e) {
			LOG.error("",e);
			return false;
		}
    	if(tradeTimeDate == null) {
    		return false;
    	}
    	TradeRecord tradeRecord = new TradeRecord();
    	tradeRecord.setTradeNumber(orderNum);
    	tradeRecord.setSysOrderId(merchantOrderNum);
    	tradeRecord.setMerchantNumber(childMercantOrderNum);
    	tradeRecord.setMerchantName(merchantName);
    	tradeRecord.setTerminalNumber(terminalNum);
    	tradeRecord.setAmount((int)(Float.parseFloat(amount)*100));
    	tradeRecord.setTradedAt(tradeTimeDate);
    	tradeRecord.setTradeTypeId(tradeTypeId);
    	tradeRecord.setTradedStatus(tradeStatusInt);
    	tradeRecord.setPoundage((int)(Float.parseFloat(tradeFee)*100));
    	tradeRecord.setPayFromAccount(cardNum);
    	tradeRecord.setAttachStatus(TradeRecord.ATTACH_STATUS_NO_CALCULATED);
    	tradeRecordMapper.insert(tradeRecord);
    	return true;
    }
    
    private String cellStringValue(Row row, Integer cellnum){
    	Cell cell = row.getCell(cellnum);
    	if(cell == null){
    		return null;
    	}
    	return cell.getStringCellValue();
    }
}
