package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.List;

import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
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

	public Page<PayChannel> findPages(int page, int pageSize, Byte status, String keys){
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = payChannelMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<PayChannel>(new PageRequest(1, pageSize), new ArrayList<PayChannel>(), count);
		}

		PageRequest request = new PageRequest(page, pageSize);
		List<PayChannel> result = payChannelMapper.findPageChannelsByKeys(request, status, keys);
		Page<PayChannel> channels = new Page<>(request, result, count);
		if (channels.getCurrentPage() > channels.getTotalPage()) {
			request = new PageRequest(channels.getTotalPage(), pageSize);
			result = payChannelMapper.findPageChannelsByKeys(request, status, keys);
			channels = new Page<>(request, result, count);
		}
		return channels;
	}
}
