package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.task.Good;
import com.comdosoft.financial.manage.domain.zhangfu.task.OutStore;
import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface OutStoreMapper {
	long countByKeys(@Param("status") Byte status, @Param("keys") String keys);
	
	List<OutStore> findPageOutStoresByKeys(@Param("pageRequest") PageRequest pageRequest,
			@Param("status") Byte status, @Param("keys") String keys);
	
	OutStore findOutStoreInfo(@Param("id") int id);
	
    List<Good> getGoodInfoInit(int id);
	
	Map<String, Object> getAddressInit(int id);
	
	Map<String, Object> getOrderIdByOutStorageId(@Param("id") int id);
	
	int saveWLInfo(@Param("wlCompany") String wlCompany,@Param("wlNum") String wlNum,@Param("orderId") int orderId);
	
	int saveTerminalNum(@Param("orderId") int orderId,@Param("goodId") int goodId,@Param("port") String port,@Param("loginId") int loginId);
	
	Map<String, Object> getWLByOrderId(@Param("orderId") int orderId);
	
	int changeStatus(@Param("status") int status,@Param("loginId") int loginId,@Param("id") int id);
	
	List<Map<String, Object>> getTerminalNum(@Param("orderId") int orderId,@Param("goodId") int goodId);
	
	String getOperater(@Param("orderId") int orderId);
	
	List<Map<String, Object>> getRemarks(@Param("id") int id);
	
	int saveRemark(@Param("loginId") int loginId,@Param("id") int id,@Param("content") String content);
	
	Map<String, Object> getInfoInit(@Param("orderId") int orderId);
	
	int saveProcessUser(@Param("processUserId") int processUserId,@Param("processUserName") String processUserName,@Param("id") int id);
}
