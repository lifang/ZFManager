package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.OpeningApplyMark;
import java.util.List;

public interface OpeningApplyMarkMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table opening_apply_marks
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table opening_apply_marks
	 * @mbggenerated
	 */
	int insert(OpeningApplyMark record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table opening_apply_marks
	 * @mbggenerated
	 */
	OpeningApplyMark selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table opening_apply_marks
	 * @mbggenerated
	 */
	List<OpeningApplyMark> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table opening_apply_marks
	 * @mbggenerated
	 */
	int updateByPrimaryKey(OpeningApplyMark record);
}