package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.utils.page.PageRequest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated
	 */
	int insert(Order record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated
	 */
	Order selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated
	 */
	List<Order> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Order record);
	
	List<Order> findPageOrdersByKeys(@Param("pageRequest") PageRequest pageRequest,
			@Param("status") Byte status, @Param("keys") String keys, @Param("factoryId") Integer factoryId);
	
	long countByKeys(@Param("status") Byte status, @Param("keys") String keys, @Param("factoryId") Integer factoryId);
	
	Order findOrderInfo(Integer id);
}