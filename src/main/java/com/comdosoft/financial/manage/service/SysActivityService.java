package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SysActivity;
import com.comdosoft.financial.manage.mapper.zhangfu.SysActivityMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quqiang on 15/3/21.
 */
@Service
public class SysActivityService {

    @Value("${page.activity.size}")
    private Integer pageSize;

    @Autowired
    private SysActivityMapper sysActivityMapper;

    public Page<SysActivity> findPages(int page) {
        long count = sysActivityMapper.count();
        if (count == 0) {
            return new Page<SysActivity>(new PageRequest(1, pageSize), new ArrayList<SysActivity>(), count);
        }
        PageRequest request = new PageRequest(page, pageSize);
        List<SysActivity> result = sysActivityMapper.findPageMessages(request);
        Page<SysActivity> messages = new Page<>(request, result, count);
        if (messages.getCurrentPage() > messages.getTotalPage()) {
            request = new PageRequest(messages.getTotalPage(), pageSize);
            result = sysActivityMapper.findPageMessages(request);
            messages = new Page<>(request, result, count);
        }
        return messages;
    }

    @Transactional(value = "transactionManager")
    public void delete(Integer id) {
        sysActivityMapper.deleteByPrimaryKey(id);
    }

    public SysActivity findInfo(Integer id){
        return sysActivityMapper.selectByPrimaryKey(id);
    }
}

