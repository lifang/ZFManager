package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.mapper.zhangfu.PayChannelMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 初审不通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public PayChannel statusFirstUnCheck(Integer id){
		PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
		if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK) {
			channel.setStatus(PayChannel.STATUS_FIRST_UN_CHECKED);
			payChannelMapper.updateByPrimaryKey(channel);
		}
		return channel;
	}

	/**
	 * 初审通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public PayChannel statusFirstCheck(Integer id){
		PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
		if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK
				|| channel.getStatus() == PayChannel.STATUS_FIRST_UN_CHECKED) {
			channel.setStatus(PayChannel.STATUS_FIRST_CHECKED);
			payChannelMapper.updateByPrimaryKey(channel);
		}
		return channel;
	}

	/**
	 * 审核不通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public PayChannel statusUnCheck(Integer id){
		PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
		if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK
				|| channel.getStatus() == PayChannel.STATUS_FIRST_CHECKED) {
			channel.setStatus(PayChannel.STATUS_UN_CHECKED);
			payChannelMapper.updateByPrimaryKey(channel);
		}
		return channel;
	}

	/**
	 * 审核通过
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public PayChannel statusCheck(Integer id, Boolean isThird){
		PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
		if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK
				|| channel.getStatus() == PayChannel.STATUS_FIRST_UN_CHECKED
				|| channel.getStatus() == PayChannel.STATUS_FIRST_CHECKED
				|| channel.getStatus() == PayChannel.STATUS_UN_CHECKED) {
			channel.setStatus(PayChannel.STATUS_CHECKED);
			payChannelMapper.updateByPrimaryKey(channel);
		}
		return channel;
	}

	/**
	 * 停止
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public PayChannel statusStop(Integer id){
		PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
		if (channel.getStatus() == PayChannel.STATUS_CHECKED) {
			channel.setStatus(PayChannel.STATUS_STOP);
			payChannelMapper.updateByPrimaryKey(channel);
		}
		return channel;
	}

	/**
	 * 启用
	 * @param id
	 * @return
	 */
	@Transactional("transactionManager")
	public PayChannel statusWaitingFirstCheck(Integer id){
		PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
		if (channel.getStatus() == PayChannel.STATUS_STOP) {
			channel.setStatus(PayChannel.STATUS_WAITING_FIRST_CHECK);
			payChannelMapper.updateByPrimaryKey(channel);
		}
		return channel;
	}
}
