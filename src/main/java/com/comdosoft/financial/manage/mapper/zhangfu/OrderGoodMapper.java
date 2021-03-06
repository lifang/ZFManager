package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
import java.util.List;

public interface OrderGoodMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_goods
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_goods
	 * @mbggenerated
	 */
	int insert(OrderGood record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_goods
	 * @mbggenerated
	 */
	OrderGood selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_goods
	 * @mbggenerated
	 */
	List<OrderGood> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_goods
	 * @mbggenerated
	 */
	int updateByPrimaryKey(OrderGood record);

	List<OrderGood> selectOrderGoods(List<Integer> orderIds);
	
	List<OrderGood> selectOrderGoodsByOrderId(Integer orderId);
	
	/**
	 * 用户订单供应删筛选
	 * @param customerId
	 * @return
	 */
	List<OrderGood> selectOrderGoodByGoodCreate(Integer customerId);
}