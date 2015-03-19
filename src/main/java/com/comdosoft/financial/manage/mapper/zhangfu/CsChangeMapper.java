package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.CsChange;
import com.comdosoft.financial.manage.utils.page.PageRequest;

public interface CsChangeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_changes
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_changes
	 * @mbggenerated
	 */
	int insert(CsChange record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_changes
	 * @mbggenerated
	 */
	CsChange selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_changes
	 * @mbggenerated
	 */
	List<CsChange> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table cs_changes
	 * @mbggenerated
	 */
	int updateByPrimaryKey(CsChange record);
	
	/**
	 * count records by keyword and status
	 * 
	 * @param status
	 *            the cs_changes status
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
	 *            the cs_changes status
	 * @param keyword
	 *            the keyword to search for apply_num
	 */
	List<CsChange> findPageSelective(
			@Param("pageRequest") PageRequest pageRequest,
			@Param("status") Byte status, @Param("keyword") String keyword);
	
	/**
	 * select record info by primary key
	 * 
	 * @param id
	 *            primary key of cs_changes
	 * @return
	 */
	CsChange selectInfoByPrimaryKey(Integer id);

}