package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;

import java.util.List;

public interface OtherRequirementMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table other_requirements
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table other_requirements
	 * @mbggenerated
	 */
	int insert(OtherRequirement record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table other_requirements
	 * @mbggenerated
	 */
	OtherRequirement selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table other_requirements
	 * @mbggenerated
	 */
	List<OtherRequirement> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table other_requirements
	 * @mbggenerated
	 */
	int updateByPrimaryKey(OtherRequirement record);

	List<OtherRequirement> selectCancelRequirements(Integer channelId);

    List<OtherRequirement> selectUpdateRequirements(Integer channelId);

    void deleteOtherRequirements(Integer channelId);

    /**
     * find other requirements by type
     * 
     * @param type {@link MaterialType}
     */
    List<OtherRequirement> findByType(int type);
}