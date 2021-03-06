package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.SysActivity;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysActivityMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_activities
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_activities
	 * @mbggenerated
	 */
	int insert(SysActivity record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_activities
	 * @mbggenerated
	 */
	SysActivity selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_activities
	 * @mbggenerated
	 */
	List<SysActivity> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_activities
	 * @mbggenerated
	 */
	int updateByPrimaryKey(SysActivity record);

	long count();

    List<SysActivity> findPageMessages(@Param("pageRequest") PageRequest pageRequest);

}