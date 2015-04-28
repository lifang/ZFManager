package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by quqiang on 15/4/28.
 */
@Service
public class NoticeService {

    private static final Map<Integer, Long> terminalIdCache= new HashMap<>();
    private static final Set<Integer> terminalIds = new HashSet<>();
    private static final Map<Integer, Integer> terminalOwnerCache= new HashMap<>();
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TerminalMapper terminalMapper;

    public void applyVideo(Integer terminalId){
        if(terminalId != null){
            if(terminalIds.contains(terminalId)){
                terminalIdCache.remove(terminalId);
            }
            terminalIdCache.put(terminalId, System.currentTimeMillis());
            terminalIds.add(terminalId);
            Terminal terminal = terminalMapper.findTerminalInfo(terminalId);
            terminalOwnerCache.put(terminalId, terminal.getPayChannel().getFactory().getCustomerId());
        }
    }

    public Integer getVideoApply(Integer customerId){
        long nowTime = System.currentTimeMillis();
        Integer terminalId = null;
        for (Iterator<Integer> it = terminalIds.iterator(); it.hasNext();) {
            Integer id = (Integer) it.next();
            Long time = terminalIdCache.get(id);
            if(time != null){
                if(nowTime - time > 300000){
                    terminalIdCache.remove(id);
                    terminalOwnerCache.remove(id);
                    it.remove();
                }else {
                    terminalId = id;
                    if(terminalOwnerCache.get(id) == customerId){
                        break;
                    }
                }
            }
        }
        return terminalId;
    }

}
