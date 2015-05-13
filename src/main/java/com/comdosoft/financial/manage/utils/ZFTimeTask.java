package com.comdosoft.financial.manage.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.mapper.trades.TradeTimeTaskMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.ZFTimeTaskMapper;

@Component("ZFTimeTask")  
public class ZFTimeTask{
	@Autowired
	TradeTimeTaskMapper trMapper;
	@Autowired
	ZFTimeTaskMapper zfMapper;
	
	
	/** 
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下） 
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT) 
     */  
  
    /** 
     * 定时卡点计算。每天凌晨 00:10 执行一次 
     */  
    @Scheduled(cron = "0 15 0 * * *")  
    public void autoCardCalculate() {
    	saveToTotalDayReports();
    	saveToDayReports();
    }  
  
    /** 
     * 心跳更新。启动时执行一次，之后每隔6分钟执行一次 
     */  
    @Scheduled(fixedRate = 1000*60*6)  
    public void heartbeat() {
    	saveByProvince();
    }  
  
    /** 
     * 卡点持久化。启动时执行一次，之后每隔2分钟执行一次 
     */  
    @Scheduled(fixedRate = 1000*60*2)  
    public void persistRecord() {
    }  
	
    /**
     * 保存到trade_total_day_reports
     */
    private void saveToTotalDayReports(){
    	int num=0;
    	int sum=0;
    	int orderNum=0;
    	int newUserNum=0;
    	int terminialNum=0;
    	
    	Map<String, Object> map1=trMapper.getAllAmount();
    	Map<String, Object> map2=trMapper.getAllNum();
    	
    	Map<String, Object> map3=zfMapper.getAllOrderNum();
    	Map<String, Object> map4=zfMapper.getAllCustomerNum();
    	Map<String, Object> map5=zfMapper.getAllTerminalNum();
    	
    	if(null!=map1){
    		num=Integer.parseInt(map1.get("num").toString());
    	}
    	if(null!=map2){
    		sum=Integer.parseInt(map2.get("num").toString());
    	}
    	if(null!=map3){
    		orderNum=Integer.parseInt(map3.get("num").toString());
    	}
    	if(null!=map4){
    		newUserNum=Integer.parseInt(map4.get("num").toString());
    	}
    	if(null!=map5){
    		terminialNum=Integer.parseInt(map5.get("num").toString());
    	}
    	int temp=trMapper.selTotalDayReports();
    	if(temp<1){
    		trMapper.saveToTotalDayReports(num, sum, orderNum, newUserNum, terminialNum);
    	}
    }
    
    /**
     * 保存到trade_day_reports
     */
    private void saveToDayReports(){
    	int num=0;
    	int sum=0;
    	int orderNum=0;
    	int newUserNum=0;
    	int terminialNum=0;
    	
    	Map<String, Object> map1=trMapper.getRecent24Amount();
    	Map<String, Object> map2=trMapper.getRecent24Num();
    	
    	Map<String, Object> map3=zfMapper.getRecent24OrderNum();
    	Map<String, Object> map4=zfMapper.getRecent24CustomerNum();
    	Map<String, Object> map5=zfMapper.getRecent24TerminalNum();
    	
    	if(null!=map1){
    		num=Integer.parseInt(map1.get("num").toString());
    	}
    	if(null!=map2){
    		sum=Integer.parseInt(map2.get("num").toString());
    	}
    	if(null!=map3){
    		orderNum=Integer.parseInt(map3.get("num").toString());
    	}
    	if(null!=map4){
    		newUserNum=Integer.parseInt(map4.get("num").toString());
    	}
    	if(null!=map5){
    		terminialNum=Integer.parseInt(map5.get("num").toString());
    	}
    	int temp=trMapper.selDayReports();
    	if(temp<1){
    		trMapper.saveToDayReports(num, sum, orderNum, newUserNum, terminialNum);
    	}
    }
    
    /**
     * 根据省 保存
     */
    @Transactional(value="transactionManager-trades",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void saveByProvince(){
    	List<Map<String, Object>> listTemp=zfMapper.getProCity();
    	
    	Map<String, Object> proMap=new HashMap<String, Object>();
    	
    	for(int i=0;i<listTemp.size();i++){
    		String proId=listTemp.get(i).get("proId").toString();
    		
    		String cityId=listTemp.get(i).get("cityId").toString();
    		
    		if(proMap.containsKey(proId)){
    			StringBuilder cityStr=(StringBuilder)proMap.get(proId);
    			cityStr.append(","+cityId);
    			proMap.remove(proId);
    			proMap.put(proId, cityStr);
    		}else{
    			StringBuilder cityStr=new StringBuilder();
    			cityStr.append(cityId);
    			proMap.put(proId, cityStr);
    		}
    	}
    	
    	for(String key:proMap.keySet()){
    		String proId=key;
    		String cityStr=proMap.get(proId).toString();
    		
    		Map<String, Object> mapTemp=trMapper.getDetailRecordByProId(cityStr);
    		//记录数据库
    		int amount=0;
    		if(null!=mapTemp.get("amount")){
    			amount=Integer.parseInt(mapTemp.get("amount").toString());
    		}
    		int num=0;
    		if(null!=mapTemp.get("num")){
    			num=Integer.parseInt(mapTemp.get("num").toString());
    		}
    		
    		trMapper.saveByProId(Integer.parseInt(proId), amount, num);
    	}
    }
}
