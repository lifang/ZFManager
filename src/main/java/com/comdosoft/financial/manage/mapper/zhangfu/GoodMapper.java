package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.utils.page.PageRequest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GoodMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	int insert(Good record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	Good selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	List<Good> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Good record);

	List<Good> findPageGoodsByKeys(@Param("pageRequest") PageRequest pageRequest, @Param("factoryId")Integer factoryId,
			@Param("status") Byte status, @Param("keys") String keys);
			
	long countByKeys(@Param("factoryId")Integer factoryId, @Param("status") Byte status, @Param("keys") String keys);
	
	Good findGoodInfo(Integer id);
	
	Good findPageRowGood(Integer id);

	List<Good> selectByStatusAndName(@Param("factoryId") Integer factoryId, @Param("status") Byte status, @Param("keys") String keys);

    long countByCategoryId(Integer categoryId);

    List<Good> selectRelativeGoods(Integer goodId);

    Good findGoodLazyInfo(Integer id);

    /**
     * @description 用于商品列表展示
     * @author Tory
     * @date 2015年3月23日 下午11:03:00
     */
    long countGoods(
    		@Param("status") Byte status,
    		@Param("goodBrandsId") Integer goodBrandsId, 
    		@Param("posCategoryId") Integer posCategoryId, 
    		@Param("signOrderWayId") Integer signOrderWayId,
    		@Param("payChannelId") Integer payChannelId,
    		@Param("cardTypeId") Integer cardTypeId,
    		@Param("tradeTypeId") Integer tradeTypeId,
    		@Param("billingCycleId") Integer billingCycleId,
    		@Param("minPrice") Integer minPrice,
    		@Param("maxPrice") Integer maxPrice,
    		@Param("hasLease") Boolean hasLease
    		);
    
    List<Good> selectGoods(
    		@Param("pageRequest") PageRequest pageRequest,
    		@Param("status") Byte status,
    		@Param("goodBrandsId") Integer goodBrandsId, 
    		@Param("posCategoryId") Integer posCategoryId, 
    		@Param("signOrderWayId") Integer signOrderWayId,
    		@Param("payChannelId") Integer payChannelId,
    		@Param("cardTypeId") Integer cardTypeId,
    		@Param("tradeTypeId") Integer tradeTypeId,
    		@Param("billingCycleId") Integer billingCycleId,
    		@Param("minPrice") Integer minPrice,
    		@Param("maxPrice") Integer maxPrice,
    		@Param("hasLease") Boolean hasLease,
    		@Param("orderBy") String orderBy,
    		@Param("orderType") String orderType
    		);
    
     /**
     * 通过id获取good列表
     * @author Tory
     * @param goodIds
     */
     List<Good> selectGoodsByIds(List<Integer> goodIds);
     
     int updateQuantity(Integer id,Integer quantity);
}