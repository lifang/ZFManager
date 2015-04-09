package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StockManageMapper {
	
	int toAfterSaleStock(@Param("account") String account,@Param("loginId") int loginId);

	int toNormalStock(@Param("account") String account,@Param("loginId") int loginId);
	
	int breakDown(@Param("account") String account,@Param("loginId") int loginId);
	
	Map<String, Object> checkAccount(@Param("account") String account);
	
	Map<String, Object> checkAccountIsInAfterSale(@Param("account") String account);
	
	Map<String, Object> isAgents(@Param("terminalId") int terminalId);
	Map<String, Object> isCancels(@Param("terminalId") int terminalId);
	Map<String, Object> isChanges(@Param("terminalId") int terminalId);
	Map<String, Object> isLeaseReturns(@Param("terminalId") int terminalId);
	Map<String, Object> isRepairs(@Param("terminalId") int terminalId);
	Map<String, Object> isReturns(@Param("terminalId") int terminalId);
}
