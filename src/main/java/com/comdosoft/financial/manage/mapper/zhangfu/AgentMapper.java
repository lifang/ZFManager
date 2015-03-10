package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgentMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agents
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agents
	 * @mbggenerated
	 */
	int insert(Agent record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agents
	 * @mbggenerated
	 */
	Agent selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agents
	 * @mbggenerated
	 */
	List<Agent> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agents
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Agent record);

    List<Agent> findPageAgentByKeys(@Param("pageRequest") PageRequest pageRequest,
                                   @Param("status") Byte status, @Param("keys") String keys);
    long countByKeys(@Param("status") Byte status, @Param("keys") String keys);

}