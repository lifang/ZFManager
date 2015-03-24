package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface CsAgentMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_agents
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_agents
	 * @mbggenerated
	 */
	int insert(CsAgent record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_agents
	 * @mbggenerated
	 */
	CsAgent selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_agents
	 * @mbggenerated
	 */
	List<CsAgent> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_agents
	 * @mbggenerated
	 */
	int updateByPrimaryKey(CsAgent record);

	/**
	 * count records by keyword and status
	 * 
	 * @param status
	 *            the cs_agents status
	 * @param keyword
	 *            the keyword to search for apply_num
	 */
	long countSelective(@Param("status") Byte status, @Param("keyword") String keyword);
	
	/**
	 * select page records by keyword and status
	 * 
	 * @param pageRequest
	 *            {@link PageRequest}
	 * @param status
	 *            the cs_agents status
	 * @param keyword
	 *            the keyword to search for apply_num
	 */
	List<CsAgent> findPageSelective(
			@Param("pageRequest") PageRequest pageRequest,
			@Param("status") Byte status, @Param("keyword") String keyword);
	
	/**
	 * dispatch process user by cs_agent_ids
	 * 
	 * @param params
	 */
	void dispatchUserByIds(Map<String, Object> params);
}
