package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SysConfig;
import com.comdosoft.financial.manage.mapper.zhangfu.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by quqiang on 15/3/19.
 */
@Service
public class SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    public List<SysConfig> findConfigs(){
        return sysConfigMapper.selectAll();
    }

    public Map<String, SysConfig> findConfigsMap(){
        List<SysConfig> sysConfigs = sysConfigMapper.selectAll();
        Map<String, SysConfig> sysConfigsMap = new HashMap<>();
        for (SysConfig sysConfig : sysConfigs){
            sysConfigsMap.put(sysConfig.getParamName(), sysConfig);
        }
        return sysConfigsMap;
    }

    @Transactional("transactionManager")
    public void edit(Map<String, Map<String, Integer>> configs){
        if(configs != null){
            for (String key : configs.keySet()){
               SysConfig sysConfig = sysConfigMapper.findByKey(key);
                Integer paramValue = configs.get(key).get("paramValue");
                if (key.equals(SysConfig.INTEGRAL_BUY_POS)
                || key.equals(SysConfig.INTEGRAL_TRADE)
                    || key.equals(SysConfig.TOTAL_MONEY)){
                    paramValue = paramValue * 100;
                }
                if(sysConfig == null){
                    sysConfig = new SysConfig();
                    sysConfig.setParamName(key);
                    sysConfig.setParamValue(paramValue+"");
                    Integer remark = configs.get(key).get("remark");
                    if(remark != null){
                        sysConfig.setRemark(remark+"");
                    }
                    sysConfigMapper.insert(sysConfig);
                } else {
                    sysConfig.setParamValue(paramValue+"");
                    Integer remark = configs.get(key).get("remark");
                    if(remark != null) {
                        sysConfig.setRemark(remark + "");
                    }
                    sysConfigMapper.updateByPrimaryKey(sysConfig);
                }
            }

        }
    }


}
