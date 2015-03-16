package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SysMessage;
import com.comdosoft.financial.manage.mapper.zhangfu.MessageReceiverMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.SysMessageMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junxi.qu on 2015/3/16.
 */
@Service
public class MessageService {

    @Value("${page.message.size}")
    private Integer pageSize;

    @Autowired
    private SysMessageMapper sysMessageMapper;
    @Autowired
    private MessageReceiverMapper messageReceiverMapper;
    public Page<SysMessage> findPages(int page) {
        long count = sysMessageMapper.count();
        if (count == 0) {
            return new Page<SysMessage>(new PageRequest(1, pageSize), new ArrayList<SysMessage>(), count);
        }
        PageRequest request = new PageRequest(page, pageSize);
        List<SysMessage> result = sysMessageMapper.findPageMessages(request);
        Page<SysMessage> messages = new Page<>(request, result, count);
        if (messages.getCurrentPage() > messages.getTotalPage()) {
            request = new PageRequest(messages.getTotalPage(), pageSize);
            result = sysMessageMapper.findPageMessages(request);
            messages = new Page<>(request, result, count);
        }
        return messages;
    }

    @Transactional("transactionManager")
    public void delete(List<Integer> ids) {
        for(Integer id : ids){
            delete(id);
        }
    }

    @Transactional("transactionManager")
    public void delete(Integer id) {
        sysMessageMapper.deleteByPrimaryKey(id);
        messageReceiverMapper.deleteBySysMessageId(id);
    }

    public SysMessage findInfo(Integer id) {
        return sysMessageMapper.selectByPrimaryKey(id);
    }
}
