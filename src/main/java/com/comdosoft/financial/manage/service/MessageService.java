package com.comdosoft.financial.manage.service;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.MessageReceiver;
import com.comdosoft.financial.manage.domain.zhangfu.SysMessage;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.MessageReceiverMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.SysMessageMapper;
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
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private PushNotificationService pushNotificationService;
    
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

    @Transactional("transactionManager")
    public void create(String title, String content, Integer customerId, Integer goodId,
    		Integer channelId, Byte customerType) throws PushClientException, PushServerException {
        SysMessage message = new SysMessage();
        message.setTitle(title);
        message.setContent(content);
        message.setCreatedAt(new Date());
        sysMessageMapper.insert(message);
        if (customerId != null){
            MessageReceiver receiver = new MessageReceiver();
            receiver.setCustomerId(customerId);
            receiver.setSysMessageId(message.getId());
            receiver.setStatus(MessageReceiver.STATUS_NO_READ);
            messageReceiverMapper.insert(receiver);
            String deviceCode = customerMapper.selectByPrimaryKey(customerId).getDeviceCode();
            if(deviceCode!=null && !"".equals(deviceCode.trim())){
            	String deviceType = deviceCode.substring(0, 1);
                String channelID = deviceCode.substring(1);
                pushNotificationService.pushMsgToSingleDevice(title,content,channelID,deviceType);
            }
        } else if(customerType != null){
            messageReceiverMapper.insertMessages(message.getId(), goodId, channelId, customerType);
            List<Customer> customers = customerMapper.getCustomers(goodId,channelId,customerType);
            String deviceType = "";
            String channelID = "";
            String deviceCode = "";
            for(Customer customer:customers){
            	deviceCode = customer.getDeviceCode();
            	if(deviceCode != null && !"".equals(deviceCode.trim())){
            		deviceType = deviceCode.substring(0, 1);
                	channelID = deviceCode.substring(1);
                	pushNotificationService.pushMsgToSingleDevice(title,content,channelID,deviceType);
            	}
            }
        }
    }
}
