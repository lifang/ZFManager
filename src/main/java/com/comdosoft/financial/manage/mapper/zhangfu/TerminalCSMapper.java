package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;

public interface TerminalCSMapper {
	/**
     * 查看换货申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeChangStatus(Map<String, Object> map);
    
    /**
     * 查看退货申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeReturn(Map<String, Object> map);
    /**
     * 查看跟新申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeUpdateStatus(Map<String, Object> map);
    /**
     * 查看注销申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeRentalReturnStatus(Map<String, Object> map);
    /**
     * POS找回密码
     * 
     * @param id
     * @return
     */
    String findPassword(Integer id);
    /**
     * 查看租赁退还申请是否有未处理中
     * 
     * @param map
     * @return
     */
    int JudgeLeaseReturn(Map<String, Object> map);
    /**
     * 提交退还申请
     * 
     * @param map
     * @return
     */
    void subRentalReturn(CsCancel csCancel);
    /**
     * 申请维修添加地址
     * 
     * @param map
     * @return
     */
    void subRepairAddress(CsReceiverAddress csReceiverAddress);
    /**
     * 申请换货添加
     * 
     * @param map
     * @return
     */
    void subChange(Map<Object, Object> map);
    /**
     * 申请退货添加
     * 
     * @param map
     * @return
     */
    void subReturn(Map<Object, Object> map);
    /**
     * 申请更新添加
     * 
     * @param map
     * @return
     */
    void subToUpdate(Map<Object, Object> map);
    /**
     * 提交注销
     * 
     * @param map
     * @return
     */
    void subLeaseReturn(Map<Object, Object> map);
    /**
     * 判断该终端是否开通
     * 
     * @param map
     * @return
     */
    int judgeOpen(int terminalId);
    /**
     * 获得终端详情
     * 
     * @param id
     * @return
     */
    Map<Object, Object> getApplyDetails(Integer id);
    
    List<Map<String, Object>> getRate(@Param("id") int id);
    /**
     * 获得租赁信息
     * 
     * @param id
     * @return
     */
    Map<String, String> getTenancy(Integer id);
    /**
     * 获得追踪记录
     * 
     * @param id
     * @return
     */
    List<Map<String, String>> getTrackRecord(Integer id);
    /**
     * 开通详情
     * 
     * @param id
     * @return
     */
    List<Map<Object, Object>> getOpeningDetails(Integer id);
    /**
     * 获得模板
     * 
     * @param id
     * @return
     */
    List<Map<String, Object>> getModule(Map<String, Object> map);
    /**
     * <!--用户收货地址 -->
     * 
     * @param customerId
     * @return
     */
    List<Map<Object, Object>> getCustomerAddress(Map<Object, Object> map);
    /**
     * <!-添加联系地址 -->
     * 
     * @param 
     * @return
     */
    void addCostometAddress(CustomerAddress customerAddress);
    
    String getCustomerIdFromTerminal(Integer customerId);
    
    Map<String, Object> getAddressInfoById(Integer id);
    /**
     * 检查终端号是否存在
     * @param map
     */
    int checkTerminalCode(Map<Object, Object> map);
    /**
     * 添加申请售后记录
     * @param csAgent
     */
    void submitAgent(CsAgent csAgent);
    /**
     * 物流信息
     * 
     * @param 
     * @return
     */
    void addCsAgentMark(Map<Object, Object> map);
    Map<String, Object> findTerminalInfo(Integer id);
}
