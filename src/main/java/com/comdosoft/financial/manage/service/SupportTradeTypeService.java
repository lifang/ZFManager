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
                                        Integer[] baseRates, Float[] floorCharges,
                                        Float[] floorProfits, Float[] topCharges, Float[] topProfits) {
        if(tradeTypeIds != null && tradeTypeIds.length > 0){
            for (int i = 0; i < tradeTypeIds.length; i++) {
                Integer id = tradeTypeIds[i];
                SupportTradeType supportTradeType = supportTradeTypeMapper.selectByPrimaryKey(id);
                supportTradeType.setTerminalRate(terminalRates[i]);
                supportTradeType.setBaseRate(baseRates[i]);
                supportTradeType.setFloorCharge(floorCharges[i]==null? null:(int)(floorCharges[i]*100));
                supportTradeType.setFloorProfit(floorProfits[i]==null? null:(int)(floorProfits[i]*100));
                supportTradeType.setTopCharge(topCharges[i]==null? null:(int)(topCharges[i]*100));
                supportTradeType.setTopProfit(topProfits[i]==null? null:(int)(topProfits[i]*100));
                supportTradeTypeMapper.updateByPrimaryKey(supportTradeType);
            }
        }

        SupportTradeType supportTradeType = supportTradeTypeMapper.selectBaseSupportTradeType(channelId);
        if (supportTradeType == null) {
            supportTradeType = new SupportTradeType();
            supportTradeType.setTradeType(SupportTradeType.TYPE_TRADE);
            supportTradeType.setBaseProfit(baseProfit);
            supportTradeType.setPayChannelId(channelId);
            supportTradeType.setSortIndex(0);
            supportTradeTypeMapper.insert(supportTradeType);
        } else {
            supportTradeType.setBaseProfit(baseProfit);
            supportTradeTypeMapper.updateByPrimaryKey(supportTradeType);
        }

    }
}
