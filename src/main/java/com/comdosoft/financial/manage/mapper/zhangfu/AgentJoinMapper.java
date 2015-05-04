package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.AgentJoin;
import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface AgentJoinMapper {

	/**
	 * 购买意向列表，类型查看
	 * @param pageRequest
	 * @param statu
	 * @param keys
	 * @param id
	 * @return
	 */
	public List<AgentJoin> findAgentJoinInfoByType(
			@Param("pageRequest") PageRequest pageRequest,
			@Param("statu") Byte statu, @Param("keys") String keys,
			@Param("id") int id);
	
	long countByKeys(@Param("statu") Byte statu, @Param("keys") String keys);
	
	
	void dispatch(Map<String, Object> params);
	
	
	int upStatus(@Param("id")Integer id,@Param("status") Integer status);
	
	AgentJoin findInfo(Integer id);
}
