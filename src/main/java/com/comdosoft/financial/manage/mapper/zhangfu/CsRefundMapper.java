package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.CsRefund;

public interface CsRefundMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_refunds
     *
     * @mbggenerated Thu Jan 29 14:34:31 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_refunds
     *
     * @mbggenerated Thu Jan 29 14:34:31 CST 2015
     */
    int insert(CsRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_refunds
     *
     * @mbggenerated Thu Jan 29 14:34:31 CST 2015
     */
    int insertSelective(CsRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_refunds
     *
     * @mbggenerated Thu Jan 29 14:34:31 CST 2015
     */
    CsRefund selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_refunds
     *
     * @mbggenerated Thu Jan 29 14:34:31 CST 2015
     */
    int updateByPrimaryKeySelective(CsRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_refunds
     *
     * @mbggenerated Thu Jan 29 14:34:31 CST 2015
     */
    int updateByPrimaryKey(CsRefund record);
}