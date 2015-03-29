/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月23日 下午9:03:13
 */
package com.comdosoft.financial.manage.controller.order;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCardType;
import com.comdosoft.financial.manage.domain.zhangfu.DictionarySignOrderWay;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodBrand;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.service.DictionaryService;
import com.comdosoft.financial.manage.service.GoodBrandService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.PosCategoryService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/good")
public class GoodController {
	@Autowired
	private GoodBrandService goodBrandService;
	@Autowired
	private PosCategoryService posCategoryService;
	@Autowired
	private PayChannelService payChannelService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private GoodService goodService;

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String createGet(HttpServletRequest request, Integer page,
			Integer goodBrandsId, Integer posCategoryId,
			Integer signOrderWayId, Model model, Integer payChannelId,
			Integer cardTypeId, Integer tradeTypeId) {
		List<GoodBrand> goodBrands = goodBrandService.selectAll();
		Collection<PosCategory> posCategorys = posCategoryService.listAll();
		List<PayChannel> payChannels = payChannelService.findCheckedChannels();
		List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService
				.listAllDictionarySignOrderWays();
		List<DictionaryCardType> dictionaryCardTypes = dictionaryService
				.listAllDictionaryCardTypes();
		List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService
				.listAllDictionaryTradeTypes();
		findPage(page, goodBrandsId, posCategoryId, signOrderWayId, model,
				payChannelId, cardTypeId, tradeTypeId);
		model.addAttribute("goodBrands", goodBrands);
		model.addAttribute("posCategorys", posCategorys);
		model.addAttribute("payChannels", payChannels);
		model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
		model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
		model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
		return "order/user/goodList";
	}

	/**
	 * @description 条件搜索
	 * @author Tory
	 * @date 2015年3月25日 下午11:55:32
	 */
	@RequestMapping(value = "/user/page", method = RequestMethod.GET)
	public String page(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId) {
		List<GoodBrand> goodBrands = goodBrandService.selectAll();
		Collection<PosCategory> posCategorys = posCategoryService.listAll();
		List<PayChannel> payChannels = payChannelService.findCheckedChannels();
		List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService
				.listAllDictionarySignOrderWays();
		List<DictionaryCardType> dictionaryCardTypes = dictionaryService
				.listAllDictionaryCardTypes();
		List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService
				.listAllDictionaryTradeTypes();
		findPage(page, goodBrandsId, posCategoryId, signOrderWayId, model,
				payChannelId, cardTypeId, tradeTypeId);
		model.addAttribute("goodBrands", goodBrands);
		model.addAttribute("posCategorys", posCategorys);
		model.addAttribute("payChannels", payChannels);
		model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
		model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
		model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
		for (GoodBrand goodBrand : goodBrands) {
			if (null != goodBrandsId && goodBrand.getId() == goodBrandsId) {
				model.addAttribute("goodBrandSelected", goodBrand);
			}
		}
		for (PosCategory posCategory : posCategorys) {
			if (null != posCategoryId && posCategory.getId() == posCategoryId) {
				model.addAttribute("posCategorySelected", posCategory);
			}
		}
		for (PayChannel payChannel : payChannels) {
			if (null != payChannelId && payChannel.getId() == payChannelId) {
				model.addAttribute("payChannelSelected", payChannel);
			}
		}
		for (DictionaryCardType dictionaryCardType : dictionaryCardTypes) {
			if (null != cardTypeId && dictionaryCardType.getId() == cardTypeId) {
				model.addAttribute("cardTypeSelected", dictionaryCardType);
			}
		}
		for (DictionaryTradeType dictionaryTradeType : dictionaryTradeTypes) {
			if (null != tradeTypeId && dictionaryTradeType.getId() == tradeTypeId) {
				model.addAttribute("tradeTypeSelected", dictionaryTradeType);
			}
		}
		for (DictionarySignOrderWay dictionarySignOrderWay : dictionarySignOrderWays) {
			if (null != signOrderWayId
					&& dictionarySignOrderWay.getId() == signOrderWayId) {
				model.addAttribute("dictionarySignOrderWaySelected",
						dictionarySignOrderWay);
			}
		}
		return "order/user/goodListFresh";
	}

	private void findPage(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId) {
		if (page == null) {
			page = 1;
		}
		Page<Good> goods = goodService.selectGoods(page, (byte) 5,
				goodBrandsId, posCategoryId, signOrderWayId, payChannelId,
				cardTypeId, tradeTypeId);
		model.addAttribute("goods", goods);
	}
	
	@RequestMapping(value = "/user/{id}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		model.addAttribute("good", good);
		return "order/user/goodDetail";
	}
}
