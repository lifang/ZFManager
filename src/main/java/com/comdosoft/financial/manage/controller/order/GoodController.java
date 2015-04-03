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

import com.comdosoft.financial.manage.domain.zhangfu.DictionaryBillingCycle;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCardType;
import com.comdosoft.financial.manage.domain.zhangfu.DictionarySignOrderWay;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodBrand;
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.service.DictionaryService;
import com.comdosoft.financial.manage.service.GoodBrandService;
import com.comdosoft.financial.manage.service.GoodCommentService;
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
	@Autowired
	private GoodCommentService goodCommentService;

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String createGet(HttpServletRequest request, Integer page,
			Integer goodBrandsId, Integer posCategoryId,
			Integer signOrderWayId, Model model, Integer payChannelId,
			Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		List<GoodBrand> goodBrands = goodBrandService.selectAll();
		Collection<PosCategory> posCategorys = posCategoryService.listAll();
		List<PayChannel> payChannels = payChannelService.findCheckedChannels();
		List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService
				.listAllDictionarySignOrderWays();
		List<DictionaryCardType> dictionaryCardTypes = dictionaryService
				.listAllDictionaryCardTypes();
		List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService
				.listAllDictionaryTradeTypes();
		List<DictionaryBillingCycle> dictionaryBillingCycles = dictionaryService
				.listAllDictionaryBillingCycles();
		findPage(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		model.addAttribute("goodBrands", goodBrands);
		model.addAttribute("posCategorys", posCategorys);
		model.addAttribute("payChannels", payChannels);
		model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
		model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
		model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
		model.addAttribute("dictionaryBillingCycles", dictionaryBillingCycles);
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
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		List<GoodBrand> goodBrands = goodBrandService.selectAll();
		Collection<PosCategory> posCategorys = posCategoryService.listAll();
		List<PayChannel> payChannels = payChannelService.findCheckedChannels();
		List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService
				.listAllDictionarySignOrderWays();
		List<DictionaryCardType> dictionaryCardTypes = dictionaryService
				.listAllDictionaryCardTypes();
		List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService
				.listAllDictionaryTradeTypes();
		List<DictionaryBillingCycle> dictionaryBillingCycles = dictionaryService
				.listAllDictionaryBillingCycles();
		findPage(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		model.addAttribute("goodBrands", goodBrands);
		model.addAttribute("posCategorys", posCategorys);
		model.addAttribute("payChannels", payChannels);
		model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
		model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
		model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
		model.addAttribute("dictionaryBillingCycles", dictionaryBillingCycles);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("hasLease", hasLease);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("orderType", orderType);
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
		if(null!=billingCycleId){
			for (DictionaryBillingCycle dictionaryBillingCycle : dictionaryBillingCycles) {
				if(billingCycleId==dictionaryBillingCycle.getId()){
					model.addAttribute("dictionaryBillingCycleSelected", dictionaryBillingCycle);
				}
			}
		}
		return "order/user/goodListFresh";
	}

	private void findPage(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		if (page == null) {
			page = 1;
		}
		Page<Good> goods = goodService.selectGoods(page, (byte) 5,
				goodBrandsId, posCategoryId, signOrderWayId, payChannelId,
				cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		model.addAttribute("goods", goods);
	}
	
	@RequestMapping(value = "/user/{id}/detail", method = RequestMethod.GET)
	public String detail(Model model,@PathVariable Integer id,Integer page, Integer payChannelId){
		if (page == null) {
			page = 1;
		}
		Good good = goodService.findGoodInfoEx(id);
		Page<GoodComment> goodComments = goodCommentService.findCommentPagesByGoodId(id, page);
		if(null==payChannelId){
			payChannelId=good.getChannels().get(0).getId();
		}
		PayChannel findChannelInfo = payChannelService.findChannelInfo(payChannelId);
		model.addAttribute("goodComments", goodComments);
		model.addAttribute("good", good);
		model.addAttribute("payChannel", findChannelInfo);
		return "order/user/goodDetail";
	}
	
	@RequestMapping(value = "/user/comment/{id}/page", method = RequestMethod.GET)
	public String commentPage(@PathVariable Integer id,Integer page, Model model){
		if (page == null) {
			page = 1;
		}
		Page<GoodComment> goodComments = goodCommentService.findCommentPagesByGoodId(id, page);
		model.addAttribute("goodComments", goodComments);
		return "order/user/pageGoodComment";
	}
}
