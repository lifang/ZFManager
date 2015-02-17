package com.comdosoft.financial.manage.controller.good;

import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("good/channel")
public class ChannelController {

	@Autowired
	private PayChannelService payChannelService ;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/channel/list";
	}

	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/channel/pageChannel";
	}

	@RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
	public String firstUnCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusFirstUnCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "channel/pos/pageRowChannel";
	}

	@RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
	public String firstCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusFirstCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
	public String unCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusUnCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/check",method=RequestMethod.GET)
	public String check(@PathVariable Integer id, String source, Boolean isThird, Model model){
		PayChannel channel = payChannelService.statusCheck(id, isThird);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/stop",method=RequestMethod.GET)
	public String stop(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusStop(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/start",method=RequestMethod.GET)
	public String start(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusWaitingFirstCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
		PayChannel channel = payChannelService.findChannelInfo(id);
		model.addAttribute("channel", channel);
		return "good/channel/info";
	}

	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<PayChannel> channels = payChannelService.findPages(page, Constants.PAGE_SIZE, status, keys);
		model.addAttribute("channels", channels);
	}
}
