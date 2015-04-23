package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.manage.mapper.zhangfu.PayChannelMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.SupportTradeTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by junxi.qu on 2015/3/1.
 */
@Service
public class SupportTradeTypeService {

    @Autowired
    private SupportTradeTypeMapper supportTradeTypeMapper;
    @Autowired
    private PayChannelMapper payChannelMapper;
    @Transactional("transactionManager")
    public void updateSupportTradeTypes(Integer channelId, Integer baseProfit,
                                        Integer[] tradeTypeIds, Integer[] terminalRates,
                                        Integer[] baseRates, Integer[] floorCharges,
                                        Integer[] floorProfits, Integer[] topCharges, Integer[] topProfits) {
        if(tradeTypeIds != null && tradeTypeIds.length > 0){
            for (int i = 0; i < tradeTypeIds.length; i++) {
                Integer id = tradeTypeIds[i];
                SupportTradeType supportTradeType = supportTradeTypeMapper.selectByPrimaryKey(id);
                supportTradeType.setTerminalRate(terminalRates[i]);
                supportTradeType.setBaseRate(baseRates[i]);
                supportTradeType.setFloorCharge(floorCharges[i]);
                supportTradeType.setFloorProfit(floorProfits[i]);
                supportTradeType.setTopCharge(topCharges[i]);
                supportTradeType.setTopProfit(topProfits[i]);
                supportTradeTypeMapper.updateByPrimaryKey(supportTradeType);
            }
        }

        SupportTradeType supportTradeType = supportTradeTypeMapper.selectBaseSupportTradeType(channelId);
        if (supportTradeType != null) {
            supportTradeType.setBaseProfit(baseProfit);
            supportTradeTypeMapper.updateByPrimaryKey(supportTradeType);
        }

    }
}
