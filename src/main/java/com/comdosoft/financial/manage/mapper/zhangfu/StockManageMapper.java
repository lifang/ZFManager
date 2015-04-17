package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StockManageMapper {
	
	int toAfterSaleStock(@Param("serialNum") String serialNum,@Param("loginId") int loginId);

	int toNormalStock(@Param("serialNum") String serialNum,@Param("loginId") int loginId);
	
	int breakDown(@Param("serialNum") String serialNum,@Param("loginId") int loginId);
	
	Map<String, Object> checkAccount(@Param("serialNum") String serialNum);
	
	Map<String, Object> checkAccountIsInAfterSale(@Param("serialNum") String serialNum);
	
	Map<String, Object> isAgents(@Param("terminalId") int terminalId);
	Map<String, Object> isCancels(@Param("terminalId") int terminalId);
	Map<String, Object> isChanges(@Param("terminalId") int terminalId);
	Map<String, Object> isLeaseReturns(@Param("terminalId") int terminalId);
	Map<String, Object> isRepairs(@Param("terminalId") int terminalId);
	Map<String, Object> isReturns(@Param("terminalId") int terminalId);
}
