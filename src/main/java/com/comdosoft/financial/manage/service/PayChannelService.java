package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.mapper.zhangfu.PayChannelMapper;

@Service
public class PayChannelService {

	@Autowired
	private PayChannelMapper payChannelMapper;
	
	public List<PayChannel> findCheckedChannelsLikeName(String name){
		return payChannelMapper.selectByStatusAndName(PayChannel.STATUS_CHECKED, "%"+name+"%");
	}
	

}
