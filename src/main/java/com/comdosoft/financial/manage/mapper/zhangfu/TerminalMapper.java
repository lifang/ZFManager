package com.comdosoft.financial.manage.mapper.zhangfu;

import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TerminalMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table terminals
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table terminals
	 * @mbggenerated
	 */
	int insert(Terminal record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table terminals
	 * @mbggenerated
	 */
	Terminal selectByPrimaryKey(Integer id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table terminals
	 * @mbggenerated
	 */
	List<Terminal> selectAll();
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table terminals
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Terminal record);
	long countCustomerTerminals(Integer customerId);
	List<Terminal> selectCustomerTerminals(Integer customerId,PageRequest request);

    List<Terminal> selectPageTerminalsByKeys(@Param("pageRequest") PageRequest pageRequest,
                                   @Param("status") Byte status, @Param("keys") String keys);

    long countByKeys(@Param("status") Byte status, @Param("keys") String keys);

    Terminal findTerminalInfo(Integer id);

    long countByAgentCode(String code);
    long countOpenByAgentCode(String code);
    
	/**
	 * set is_return_cs_depots to <i>false</i> by serial_num
	 * 
	 * @param serialNums
	 *            array of serial_num
	 * @return
	 */
	int closeCsReturnDepotsByNums(String[] serialNums);

}
