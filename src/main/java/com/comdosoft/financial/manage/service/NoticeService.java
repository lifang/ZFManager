package com.comdosoft.financial.manage.service;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by quqiang on 15/4/28.
 */
@Service
public class NoticeService {

    private static final Map<Integer, Long> terminalIdCache= new TreeMap<>();
    private static final Set<Integer> terminalIds = new HashSet<>();
    public void applyVideo(Integer terminalId){
        if(terminalId != null){
            if(terminalIds.contains(terminalId)){
                terminalIdCache.remove(terminalId);
            }
            terminalIdCache.put(terminalId, System.currentTimeMillis());
        }
    }

    public Integer getVideoApply(Integer customerId){
        long nowTime = System.currentTimeMillis();
        Integer terminalId = null;
        for (Iterator<Integer> it = terminalIds.iterator(); it.hasNext();) {
            Integer id = (Integer) it.next();
            Long time = terminalIdCache.get(id);
            if(time != null){
                if(nowTime - time > 60000){
                    terminalIdCache.remove(id);
                    it.remove();
                } else{
                    terminalId = id;
                    return terminalId;
                }
            }
        }
        return terminalId;
    }

}
