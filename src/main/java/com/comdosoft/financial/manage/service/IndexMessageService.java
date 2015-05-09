package com.comdosoft.financial.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsChange;
import com.comdosoft.financial.manage.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.manage.domain.zhangfu.CsRefund;
import com.comdosoft.financial.manage.domain.zhangfu.CsRepair;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturn;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.manage.mapper.zhangfu.IndexMessageMapper;


@Service
public class IndexMessageService {

   
    @Autowired
    private IndexMessageMapper indexMessageMapper;
   

    /**
     * 评论待审核总数
     *
     * @return
     */
    public int CommentModeration() {
    	return indexMessageMapper.CommentModeration(GoodComment.STATUS_WAITING);
    }

    /**
     * 产品待审核总数
     *
     * @return
     */
    public int GoodModeration() {
    	return indexMessageMapper.GoodModeration(Good.STATUS_WAITING_FIRST_CHECK);
    }
   
    /**
     * 开通申请待审核总数
     *
     * @return
     */
    public int openingAppliesModeration() {
    	return indexMessageMapper.openingAppliesModeration(OpeningApplie.STATUS_WAITING_FIRST_CHECK);
    }

    /**
     * 退货申请待审核总数
     *
     * @return
     */
    public int returnModeration() {
    	return indexMessageMapper.returnModeration(CsReturn.STATUS_1);
    }
    
    /**
     * 换货申请待审核总数
     *
     * @return
     */
    public int changeModeration() {
    	return indexMessageMapper.changeModeration(CsChange.STATUS_1);
    }
    
    /**
     * 维修申请待审核总数
     *
     * @return
     */
    public int repairsModeration() {
    	return indexMessageMapper.repairsModeration(CsRepair.STATUS_1);
    }
    
    /**
     * 注销申请待审核总数
     *
     * @return
     */
    public int cancelsModeration() {
    	return indexMessageMapper.cancelsModeration(CsCancel.STATUS_1);
    }
    
    /**
     * 资料更新申请待审核总数
     *
     * @return
     */
    public int updateInfosModeration() {
    	return indexMessageMapper.updateInfosModeration(OpeningApplie.STATUS_WAITING_FIRST_CHECK);
    }
    
    /**
     * 发货单申请待审核总数
     *
     * @return
     */
    public int storagesModeration() {
    	return indexMessageMapper.storagesModeration(CsOutStorage.STATUS_WAIT_PROCESS);
    }
    
    /**
     * 退款单申请待审核总数
     *
     * @return
     */
    public int srefundsModeration() {
    	return indexMessageMapper.srefundsModeration(CsRefund.STATIC_1);
    }
    
    /**
     * 代理商售后申请待审核总数
     *
     * @return
     */
    public int agentModeration() {
    	return indexMessageMapper.agentModeration(CsAgent.STATIC_1);
    }
}

