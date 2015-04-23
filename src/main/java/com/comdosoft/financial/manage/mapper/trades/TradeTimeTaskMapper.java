package com.comdosoft.financial.manage.mapper.trades;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TradeTimeTaskMapper {
	//总的
	Map<String, Object> getAllAmount();
	Map<String, Object> getAllNum();
	//最近24小时
	Map<String, Object> getRecent24Amount();
	Map<String, Object> getRecent24Num();
	
	List<Map<String, Object>> getDetailRecordByCityId();
	
	int saveToTotalDayReports(@Param("num") int num,@Param("sum") int sum,@Param("orderNum") int orderNum,
			@Param("newUserNum") int newUserNum,@Param("terminialNum") int terminialNum);
	
	int saveToDayReports(@Param("num") int num,@Param("sum") int sum,@Param("orderNum") int orderNum,
			@Param("newUserNum") int newUserNum,@Param("terminialNum") int terminialNum);
	
	Map<String, Object> getDetailRecordByProId(@Param("cityStr") String cityStr);
	
	int saveByProId(@Param("proId") int proId,@Param("amount") int amount,@Param("num") int num);
}
