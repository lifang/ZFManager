package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturn;
import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface CsLeaseReturnMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_lease_returns
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_lease_returns
	 * @mbggenerated
	 */
	int insert(CsLeaseReturn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_lease_returns
	 * @mbggenerated
	 */
	CsLeaseReturn selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_lease_returns
	 * @mbggenerated
	 */
	List<CsLeaseReturn> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_lease_returns
	 * @mbggenerated
	 */
	int updateByPrimaryKey(CsLeaseReturn record);
	
	/**
	 * count records by keyword and status
	 * 
	 * @param status
	 *            the cs_lease_returns status
	 * @param keyword
	 *            the keyword to search for apply_num
	 */
	long countSelective(@Param("status") Integer status, @Param("keyword") String keyword);
	
	/**
	 * select page records by keyword and status
	 * 
	 * @param pageRequest
	 *            {@link PageRequest}
	 * @param status
	 *            the cs_lease_returns status
	 * @param keyword
	 *            the keyword to search for apply_num
	 */
	List<CsLeaseReturn> findPageSelective(
			@Param("pageRequest") PageRequest pageRequest,
			@Param("customerId") Integer customerId,
			@Param("status") Integer status, @Param("keyword") String keyword);
	
	/**
	 * select record info by primary key
	 * 
	 * @param id
	 *            primary key of cs_lease_returns
	 * @return
	 */
	CsLeaseReturn selectInfoByPrimaryKey(Integer id);
	
	/**
	 * dispatch process user by cs_lease_return_ids
	 * 
	 * @param params
	 */
	void dispatchUserByIds(Map<String, Object> params);

}