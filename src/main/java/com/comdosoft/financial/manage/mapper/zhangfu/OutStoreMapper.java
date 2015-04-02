package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.OutStore;
import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface OutStoreMapper {
	long countByKeys(@Param("status") Byte status, @Param("keys") String keys);
	
	List<OutStore> findPageOutStoresByKeys(@Param("pageRequest") PageRequest pageRequest,
			@Param("status") Byte status, @Param("keys") String keys);
}
