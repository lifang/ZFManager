package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;

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
	
	String getAddressInit(int id);
}
