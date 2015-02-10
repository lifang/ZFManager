package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodCommentMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table good_comments
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table good_comments
	 * @mbggenerated
	 */
	int insert(GoodComment record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table good_comments
	 * @mbggenerated
	 */
	GoodComment selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table good_comments
	 * @mbggenerated
	 */
	List<GoodComment> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table good_comments
	 * @mbggenerated
	 */
	int updateByPrimaryKey(GoodComment record);

	long countByStatus(@Param("status") Byte status);

    List<GoodComment> findPageCommentsByStatus(@Param("pageRequest") PageRequest pageRequest,
                                               @Param("status") Byte statuss);
}