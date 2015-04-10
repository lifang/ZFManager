package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

public interface ZFTimeTaskMapper {
	
	//总成交单数，成员数
	Map<String, Object> getAllOrderNum();
	Map<String, Object> getAllCustomerNum();
	Map<String, Object> getAllTerminalNum();
	//最近24小时
	Map<String, Object> getRecent24OrderNum();
	Map<String, Object> getRecent24CustomerNum();
	Map<String, Object> getRecent24TerminalNum();
	
	List<Map<String, Object>> getProCity();
	
}
