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
	
	int saveWLInfo(@Param("wlCompany") String wlCompany,@Param("wlNum") String wlNum,@Param("orderId") int orderId,
				@Param("csOutStorageId") int csOutStorageId);
	
	int saveTerminalNum(@Param("orderId") int orderId,@Param("goodId") int goodId,@Param("port") String port,@Param("loginId") int loginId,
				@Param("quantity") int quantity,@Param("csOutStorageId") int csOutStorageId);
	
	Map<String, Object> getInOutStorageTerminalInfo(@Param("orderId") int orderId,@Param("goodId") int goodId,@Param("port") String port,@Param("csOutStorageId") int csOutStorageId);
	//更新销售数量
	int updateGoodsVolumeNumber(@Param("goodId") int goodId);
	//更新批购数量
	int updateGoodsPurchaseNumber(@Param("goodId") int goodId);
	
	
	List<Map<String, Object>> getWLByOrderId(@Param("orderId") int orderId);
	
	int changeStatus(@Param("status") int status,@Param("loginId") int loginId,@Param("id") int id);
	
	List<Map<String, Object>> getTerminalNum(@Param("orderId") int orderId,@Param("goodId") int goodId);
	
	String getOperater(@Param("orderId") int orderId);
	
	String getProcessName(@Param("id") int customerId);
	
	Map<String, Object> getCutomerTypeByOrderId(@Param("orderId") int orderId);
	
	int getPayChannleIdByOrderId(@Param("orderId") int orderId,@Param("goodId") int goodId);
	
	List<Map<String, Object>> getRemarks(@Param("id") int id);
	
	int saveRemark(@Param("loginId") int loginId,@Param("id") int id,@Param("content") String content);
	
	int saveOrderMarks(@Param("loginId") int loginId,@Param("orderId") int id,@Param("content") String content);
	
	List<Map<String, Object>> getInfoInit(@Param("orderId") int orderId);
	
	int saveProcessUser(@Param("processUserId") int processUserId,@Param("processUserName") String processUserName,@Param("id") int id);
	
	Map<String, Object> getAgentIdByCustomerId(@Param("customerId") String customerId);
	
	int updateTerminals(@Param("customerId") String customerId,@Param("agentId") String agentId,
			@Param("orderId") int orderId,@Param("serialNum") String serialNum,@Param("payChannelId") int payChannelId);
	
	int updateOrderStatus(@Param("status") int status,@Param("orderId") int orderId);
	
	int updateCsOutStorages(@Param("status") int status,@Param("quantity") int quantity,@Param("terminalNum") String terminalNum,@Param("id") int id);
	
	int getQuantityByOrderGood(@Param("goodId") int goodId,@Param("orderId") int orderId);
	
	Map<String, Object> getInOutStorageInfo(@Param("orderId") int orderId,@Param("goodId") int goodId);
	
	List<Map<String, Object>> getOrderGoodQuantity(@Param("orderId") int orderId);
	
	String getNameByLoginId(@Param("loginId") int loginId);
	
	int getTerminalIsUsed(@Param("serialNum") String serialNum);
	
	int getCsOutStorageStatus(@Param("id") int id);
	
	List<Map<String, Object>> getTerminalsInfo(@Param("serialNum") String serialNum);
	
	List<Map<String, Object>> getInOutStoragesAllInfo(@Param("orderId") int orderId,@Param("csOutStorageId") int csOutStorageId);
	
	int getAllQuantityCsOutStorage(@Param("orderId") int orderId);

	int getQuantityInOutStorage(@Param("orderId") int orderId,@Param("goodId") int goodId,@Param("csOutStorageId") int csOutStorageId);
	
	String getOrderNumberById(@Param("orderId") int orderId);
	
	List<Map<String, Object>> getPayChannelList();
}
