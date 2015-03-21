package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.WebMessage;
import com.comdosoft.financial.manage.mapper.zhangfu.WebMessageMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by quqiang on 15/3/20.
 */
@Service
public class WebMessageService {

    @Value("${page.webMessage.size}")
    private Integer pageSize;
    @Autowired
    private WebMessageMapper webMessageMapper;

    public Page<WebMessage> findPages(int page) {
        long count = webMessageMapper.count();
        if (count == 0) {
            return new Page<WebMessage>(new PageRequest(1, pageSize), new ArrayList<WebMessage>(), count);
        }
        PageRequest request = new PageRequest(page, pageSize);
        List<WebMessage> result = webMessageMapper.findPageMessages(request);
        Page<WebMessage> messages = new Page<>(request, result, count);
        if (messages.getCurrentPage() > messages.getTotalPage()) {
            request = new PageRequest(messages.getTotalPage(), pageSize);
            result = webMessageMapper.findPageMessages(request);
            messages = new Page<>(request, result, count);
        }
        return messages;
    }

    @Transactional("transactionManager")
    public void delete(Integer id) {
        webMessageMapper.deleteByPrimaryKey(id);
    }

    @Transactional("transactionManager")
    public WebMessage findInfo(Integer id) {
        return webMessageMapper.selectByPrimaryKey(id);
    }

    @Transactional("transactionManager")
    public void top(Integer id) {
        WebMessage message = webMessageMapper.selectByPrimaryKey(id);
        message.setIsPlacedTop(true);
        webMessageMapper.updateByPrimaryKey(message);
    }

    @Transactional("transactionManager")
    public void cancelTop(Integer id) {
        WebMessage message = webMessageMapper.selectByPrimaryKey(id);
        message.setIsPlacedTop(false);
        message.setUpdatedAt(new Date());
        webMessageMapper.updateByPrimaryKey(message);
    }

    @Transactional("transactionManager")
    public void create(String title, String content) {
        WebMessage message = new WebMessage();
        message.setTitle(title);
        message.setContent(content);
        message.setIsPlacedTop(false);
        message.setCreateAt(new Date());
        message.setUpdatedAt(new Date());
        webMessageMapper.insert(message);
    }

    @Transactional("transactionManager")
    public void edit(Integer id, String title, String content) {
        WebMessage message = webMessageMapper.selectByPrimaryKey(id);
        message.setTitle(title);
        message.setContent(content);
        message.setUpdatedAt(new Date());
        webMessageMapper.updateByPrimaryKey(message);
    }
}
