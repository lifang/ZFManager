package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface RefundMapper {
	long getRefundCount(@Param("status") Byte status, @Param("orderNumber") String orderNumber);
	
	List<Object> findPageRefundByKeys(@Param("pageRequest") PageRequest pageRequest,
			@Param("status") Byte status, @Param("orderNumber") String keys);
	
	Map<Object, Object> getRefundDetails(@Param("id") int id);
	
	List<Map<Object, Object>> getRefundByDetailRecord(@Param("id") int id);
	
	void addRefundMark(@Param("refundId") int refundId,@Param("content") String content,@Param("customerId") int customerId);
	
	void updateRefund(@Param("id") int id,@Param("returnVoucherFilePath") String returnVoucherFilePath);
	
	void updsateRefundStatus(@Param("id") int id,@Param("status") int status);
	
	void dispatchUserByIds(Map<String, Object> params);
}
