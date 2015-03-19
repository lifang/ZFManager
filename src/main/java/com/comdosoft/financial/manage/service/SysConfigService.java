package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SysConfig;
import com.comdosoft.financial.manage.mapper.zhangfu.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
