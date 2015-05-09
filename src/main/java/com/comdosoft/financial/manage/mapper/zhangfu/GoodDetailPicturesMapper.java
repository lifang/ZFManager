package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.GoodDetailPictures;
import java.util.List;

public interface GoodDetailPicturesMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	int deleteById(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	int insert(GoodDetailPictures goodDetailPictures);

	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goods
	 * @mbggenerated
	 */
	List<GoodDetailPictures> selectAll(Integer goodId);
	
	GoodDetailPictures selectById(Integer id);
}